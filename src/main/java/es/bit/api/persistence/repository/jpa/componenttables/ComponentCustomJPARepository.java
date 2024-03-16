package es.bit.api.persistence.repository.jpa.componenttables;

import es.bit.api.persistence.model.componenttables.Component;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class ComponentCustomJPARepository implements IComponentCustomJPARepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Component> findAll(String search) {
        List<String> conditions = new ArrayList<>();
        Map<String, Object> parameters = new HashMap<>();

        StringBuilder queryBuilder = new StringBuilder("SELECT c FROM Component c JOIN FETCH c.componentType ct JOIN FETCH c.manufacturer m JOIN FETCH c.lighting l");
        generateQueryParameters(search, conditions, parameters);

        if (!conditions.isEmpty()) {
            queryBuilder.append(" WHERE ").append(StringUtils.join(conditions, " AND "));
        }

        Query jpaQuery = entityManager.createQuery(queryBuilder.toString());

        for (String key : parameters.keySet()) {
            jpaQuery.setParameter(key, parameters.get(key));
        }

        return jpaQuery.getResultList();
    }


    private void generateQueryParameters(String search, List<String> conditions, Map<String, Object> parameters) {
        Pattern pattern = Pattern.compile("([\\w\\d]+)\\.([\\w\\d]+)(:|<=|<|>=|>)([^,]+)");
        Matcher matcher = pattern.matcher(search);

        int counter = 0;

        while (matcher.find()) {
            String entity = matcher.group(1);
            String attribute = matcher.group(2);
            String condition = matcher.group(3);
            String value = matcher.group(4);

            counter++;

            buildCondition(conditions, parameters, entity, attribute, condition, URLEncoder.encode(value.toLowerCase(), StandardCharsets.UTF_8), counter);
        }
    }

    private void buildCondition(List<String> conditions, Map<String, Object> parameters, String entity, String attribute, String condition, String value, int counter) {
        String parameterIdentifier = "param" + entity.substring(0, 1) + attribute.substring(0, 1) + counter;

        switch (entity) {
            case "component":
                switch (attribute) {
                    case "name":
                        switch (condition) {
                            case ":":
                                addCondition(conditions, parameters, "LOWER(c.name) LIKE :", parameterIdentifier, value, counter, entity, attribute, true);
                                break;

                            default:
                                throw new IllegalArgumentException("Condition not supported for component.name: " + condition);
                        }
                        break;

                    case "price":
                        switch (condition) {
                            case ":":
                                addCondition(conditions, parameters, " c.price LIKE :", parameterIdentifier, value, counter, entity, attribute, false);
                                break;

                            case "<":
                            case "<=":
                            case ">=":
                            case ">":
                                addCondition(conditions, parameters, " c.price " + condition + ":", parameterIdentifier, value, counter, entity, attribute, false);
                                break;

                            default:
                                throw new IllegalArgumentException("Condition not supported for component.price: " + condition);
                        }
                        break;

                    default:
                        throw new IllegalArgumentException("Attribute not supported for component entity: " + attribute);
                }
                break;

            case "manufacturer": {
                switch (attribute) {
                    case "name":
                        switch (condition) {
                            case ":":
                                addCondition(conditions, parameters, "LOWER(m.name) LIKE :", parameterIdentifier, value, counter, entity, attribute, true);
                                break;

                            default:
                                throw new IllegalArgumentException("Condition not supported for manufacturer.name: " + condition);
                        }
                        break;

                    default:
                        throw new IllegalArgumentException("Attribute not supported for manufacturer entity: " + attribute);
                }
            }
            break;

            case "lighting": {
                switch (attribute) {
                    case "name":
                        switch (condition) {
                            case ":":
                                addCondition(conditions, parameters, "LOWER(l.name) LIKE :", parameterIdentifier, value, counter, entity, attribute, true);
                                break;

                            default:
                                throw new IllegalArgumentException("Condition not supported for lighting.name: " + condition);
                        }
                        break;

                    default:
                        throw new IllegalArgumentException("Attribute not supported for lighting entity: " + attribute);
                }
            }

            break;

            case "componentType": {
                switch (attribute) {
                    case "name":
                        switch (condition) {
                            case ":":
                                addCondition(conditions, parameters, "LOWER(ct.name) LIKE :", parameterIdentifier, value, counter, entity, attribute, true);
                                break;

                            default:
                                throw new IllegalArgumentException("Condition not supported for componentType.name: " + condition);
                        }
                        break;

                    default:
                        throw new IllegalArgumentException("Attribute not supported for componentType entity: " + attribute);
                }
            }
            break;

            default:
                throw new IllegalArgumentException("Entity not supported: " + entity);
        }
    }

    private void addCondition(List<String> conditions, Map<String, Object> parameters, String condition, String identifier, String value, int counter, String entity, String attribute, boolean isString) {
        if (value.contains("+")) {
            String [] searchTerms = value.split("\\+");

            for (int i = 0; i < searchTerms.length; i++) {
                conditions.add(condition + identifier);
                parameters.put(identifier, "%" + searchTerms[i] + "%");
                counter++;
                identifier = "param" + entity.substring(0, 1) + attribute.substring(0, 1) + counter;
            }
        } else {
            conditions.add(condition + identifier);

            if (isString) {
                parameters.put(identifier, "%" + value + "%");
            } else {
                parameters.put(identifier, value);
            }
        }
    }
}
