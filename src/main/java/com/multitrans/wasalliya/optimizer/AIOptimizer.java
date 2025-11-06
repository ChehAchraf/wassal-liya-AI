package com.multitrans.wasalliya.optimizer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multitrans.wasalliya.model.Delivery;
import com.multitrans.wasalliya.model.Vehicale;
import com.multitrans.wasalliya.model.Warehouse;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "app.optimizer.strategy", havingValue = "ai")
public class AIOptimizer implements TourOptimizer {

    private final ChatModel chatModel; 
    private final ObjectMapper objectMapper;

    @Autowired
    public AIOptimizer(ChatModel chatModel, ObjectMapper objectMapper) {
        this.chatModel = chatModel;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Delivery> calculateOptimalTour(Warehouse warehouse, List<Delivery> pendingDeliveries, Vehicale vehicle) {

        try {

            List<Map<String, Object>> deliveriesSimple = pendingDeliveries.stream()
                    .map(d ->{
                        // كنصاوبو Map ونفرضو النوع ديالها
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", d.getId());
                        map.put("lat", d.getLatitude());
                        map.put("lon", d.getLongitude());
                        map.put("weight", d.getWeight());
                        return map;
                    })
                    .collect(Collectors.toList());

            String deliveriesJson = objectMapper.writeValueAsString(deliveriesSimple);
            String warehouseJson = objectMapper.writeValueAsString(Map.of("lat", warehouse.getLatitude(), "lon", warehouse.getLongitude()));


            String promptText = """
                    You are an expert logistics AI. Optimize this delivery route for minimal distance.
                    
                    WAREHOUSE LOCATION: %s
                    
                    DELIVERIES TO OPTIMIZE (JSON):
                    %s
                    
                    CONSTRAINTS:
                    - Start at Warehouse -> Visit ALL deliveries -> End at Warehouse.
                    - Respect vehicle max weight: %.2f kg.
                    
                    OUTPUT FORMAT (CRITICAL):
                    You MUST return ONLY a raw JSON object (no markdown, no explanations outside JSON).
                    Structure:
                    {
                      "ordered_ids": [id_first_delivery, id_second_delivery, ...],
                      "reasoning": "Explain briefly why this route is optimal."
                    }
                    """.formatted(warehouseJson, deliveriesJson, vehicle.getMaxWeight());

            System.out.println("🤖 Asking Tinyllama to optimize...");
            ChatResponse response = chatModel.call(new Prompt(promptText));
            String aiResponseRaw = response.getResult().getOutput().getContent();
            System.out.println("🤖 tinyllama Answer: " + aiResponseRaw);
            String cleanJson = aiResponseRaw;
            int firstBracket = aiResponseRaw.indexOf("{");
            int lastBracket = aiResponseRaw.lastIndexOf("}");

            if (firstBracket != -1 && lastBracket != -1 && firstBracket < lastBracket) {
                cleanJson = aiResponseRaw.substring(firstBracket, lastBracket + 1);
            } else {
                System.err.println("🚨 Could not find JSON in AI response!");
            }            JsonNode rootNode = objectMapper.readTree(cleanJson);

            JsonNode idsNode = rootNode.get("ordered_ids");
            List<Long> orderedIds = new ArrayList<>();
            if (idsNode.isArray()) {
                for (JsonNode idNode : idsNode) {
                    orderedIds.add(idNode.asLong());
                }
            }

            List<Delivery> orderedDeliveries = new ArrayList<>();
            for (Long id : orderedIds) {
                pendingDeliveries.stream()
                        .filter(d -> d.getId().equals(id))
                        .findFirst()
                        .ifPresent(orderedDeliveries::add);
            }

            for (Delivery d : pendingDeliveries) {
                if (!orderedDeliveries.contains(d)) {
                    orderedDeliveries.add(d);
                }
            }

            return orderedDeliveries;

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing AI response or inputs", e);
        } catch (Exception e) {
            System.err.println("🚨 AI Optimization failed, falling back to original order: " + e.getMessage());
            return pendingDeliveries;
        }
    }
}