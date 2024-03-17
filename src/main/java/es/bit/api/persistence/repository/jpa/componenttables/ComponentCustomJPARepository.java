package es.bit.api.persistence.repository.jpa.componenttables;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
public class ComponentCustomJPARepository implements IComponentCustomJPARepository<Object> {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    IComponentJPARepository componentJPARepository;


    @Override
    public Page<Object> findAll(String search, Pageable pageable) {
        List<String> conditions = new ArrayList<>();
        Map<String, Object> parameters = new HashMap<>();

        StringBuilder queryBuilder = new StringBuilder("SELECT c FROM Component c JOIN FETCH c.componentType ct JOIN FETCH c.manufacturer m JOIN FETCH c.lighting l");
        generateQueryParameters(search, conditions, parameters);

        if (!conditions.isEmpty()) {
            queryBuilder.append(" WHERE ").append(StringUtils.join(conditions, " AND "));
        }

        Query jpaQuery = entityManager.createQuery(queryBuilder.toString());

        for (Map.Entry<String, Object> entry: parameters.entrySet()) {
            jpaQuery.setParameter(entry.getKey(), entry.getValue());
        }

        long total = componentJPARepository.count();

        jpaQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        jpaQuery.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(jpaQuery.getResultList(), pageable, total);
    }


    private void generateQueryParameters(String search, List<String> conditions, Map<String, Object> parameters) {
        Pattern pattern = Pattern.compile("(\\w+)\\.(\\w+)(:|<=|<|>=|>)([^,]+)");
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
        String parameterIdentifier = "param" + entity.charAt(0) + attribute.charAt(0) + counter;

        switch (entity) {
            case "component":
                switch (attribute) {
                    case "name":
                        if (condition.equals(":")) {
                            addCondition(conditions, parameters, "LOWER(c.name) LIKE :", parameterIdentifier, value, counter, entity, attribute, true);
                        } else {
                            throw new IllegalArgumentException("Condition not supported for component.name: " + condition);
                        }
                        break;

                    case "price":
                        switch (condition) {
                            case ":":
                                addCondition(conditions, parameters, " c.price LIKE :", parameterIdentifier, value, counter, entity, attribute, false);
                                break;

                            case "<", "<=", ">=", ">":
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
                if (attribute.equals("name")) {
                    if (condition.equals(":")) {
                        addCondition(conditions, parameters, "LOWER(m.name) LIKE :", parameterIdentifier, value, counter, entity, attribute, true);
                    } else {
                        throw new IllegalArgumentException("Condition not supported for manufacturer.name: " + condition);
                    }
                } else {
                    throw new IllegalArgumentException("Attribute not supported for manufacturer entity: " + attribute);
                }
            }
            break;

            case "lighting": {
                if (attribute.equals("name")) {
                    if (condition.equals(":")) {
                        addCondition(conditions, parameters, "LOWER(l.name) LIKE :", parameterIdentifier, value, counter, entity, attribute, true);
                    } else {
                        throw new IllegalArgumentException("Condition not supported for lighting.name: " + condition);
                    }
                } else {
                    throw new IllegalArgumentException("Attribute not supported for lighting entity: " + attribute);
                }
            }

            break;

            case "componentType": {
                if (attribute.equals("name")) {
                    if (condition.equals(":")) {
                        addCondition(conditions, parameters, "LOWER(ct.name) LIKE :", parameterIdentifier, value, counter, entity, attribute, false);
                    } else {
                        throw new IllegalArgumentException("Condition not supported for componentType.name: " + condition);
                    }
                } else {
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

            for (String searchTerm : searchTerms) {
                conditions.add(condition + identifier);
                parameters.put(identifier, "%" + searchTerm + "%");
                counter++;
                identifier = "param" + entity.charAt(0) + attribute.charAt(0) + counter;
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
