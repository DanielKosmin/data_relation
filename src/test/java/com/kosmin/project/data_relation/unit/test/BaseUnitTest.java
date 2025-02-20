package com.kosmin.project.data_relation.unit.test;

import com.kosmin.project.data_relation.config.SqlQueriesConfig;
import com.kosmin.project.data_relation.config.TableInsertConfig;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Map;
import java.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@ExtendWith(MockitoExtension.class)
public abstract class BaseUnitTest {

  protected Validator validator;
  protected SqlQueriesConfig sqlQueriesConfig;
  protected TableInsertConfig tableInsertConfig;
  protected static final String INSERT_CHECKING_RECORDS = "insert-checking-records";
  protected static final String INSERT_INTO_CREDIT_TABLE = "insert-credit-records";
  protected static final String CLEAR_CHECKING_RECORDS = "clear-checking-records";
  protected static final String DROP_CHECKING_RECORDS = "drop-checking-records";
  protected static final String CLEAR_CREDIT_RECORDS = "clear-credit-records";
  protected static final String DROP_CREDIT_RECORDS = "drop-credit-records";
  protected static final String CREATE_CHECKING_TABLE = "create-banking-table";
  protected static final String CREATE_CREDIT_TABLE = "create-credit-table";

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();

    Properties properties = loadYamlProperties();
    String checkingQuery = properties.getProperty("queries.map." + INSERT_CHECKING_RECORDS);
    String creditQuery = properties.getProperty("queries.map." + INSERT_INTO_CREDIT_TABLE);
    String clearCheckingQuery = properties.getProperty("queries.map." + CLEAR_CHECKING_RECORDS);
    String dropCheckingQuery = properties.getProperty("queries.map." + DROP_CHECKING_RECORDS);
    String clearCreditQuery = properties.getProperty("queries.map." + CLEAR_CREDIT_RECORDS);
    String dropCreditQuery = properties.getProperty("queries.map." + DROP_CREDIT_RECORDS);
    String checkingCreationQuery = properties.getProperty("tables." + CREATE_CHECKING_TABLE);
    String creditCreationQuery = properties.getProperty("tables." + CREATE_CREDIT_TABLE);

    sqlQueriesConfig =
        new SqlQueriesConfig(
            Map.of(
                INSERT_CHECKING_RECORDS, checkingQuery,
                INSERT_INTO_CREDIT_TABLE, creditQuery,
                CLEAR_CHECKING_RECORDS, clearCheckingQuery,
                DROP_CHECKING_RECORDS, dropCheckingQuery,
                CLEAR_CREDIT_RECORDS, clearCreditQuery,
                DROP_CREDIT_RECORDS, dropCreditQuery));
    tableInsertConfig = new TableInsertConfig(checkingCreationQuery, creditCreationQuery);
  }

  private Properties loadYamlProperties() {
    final Resource resource = new ClassPathResource("queries.yml");
    final YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
    yamlPropertiesFactoryBean.setResources(resource);
    return yamlPropertiesFactoryBean.getObject();
  }
}
