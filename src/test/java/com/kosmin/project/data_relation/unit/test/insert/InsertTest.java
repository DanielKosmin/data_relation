package com.kosmin.project.data_relation.unit.test.insert;

import com.kosmin.project.data_relation.config.SqlQueriesConfig;
import com.kosmin.project.data_relation.model.repository.CheckingModel;
import com.kosmin.project.data_relation.model.repository.CreditModel;
import com.kosmin.project.data_relation.repository.insert.InsertCheckingRecords;
import com.kosmin.project.data_relation.repository.insert.InsertCreditRecords;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@ExtendWith(MockitoExtension.class)
public class InsertTest {

  @Mock private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private static final String INSERT_CHECKING_RECORDS = "insert-checking-records";
  private static final String INSERT_INTO_CREDIT_TABLE = "insert-credit-records";
  private InsertCheckingRecords insertCheckingRecords;
  private InsertCreditRecords insertCreditRecords;
  private SqlQueriesConfig sqlQueriesConfig;

  @BeforeEach
  void setUp() {
    Properties properties = loadYamlProperties();
    String checkingQuery = properties.getProperty("queries.map." + INSERT_CHECKING_RECORDS);
    String creditQuery = properties.getProperty("queries.map." + INSERT_INTO_CREDIT_TABLE);

    sqlQueriesConfig =
        new SqlQueriesConfig(
            Map.of(
                INSERT_CHECKING_RECORDS, checkingQuery,
                INSERT_INTO_CREDIT_TABLE, creditQuery));

    insertCheckingRecords = new InsertCheckingRecords(namedParameterJdbcTemplate, sqlQueriesConfig);
    insertCreditRecords = new InsertCreditRecords(namedParameterJdbcTemplate, sqlQueriesConfig);
  }

  @Test
  @DisplayName("insert records into checking table")
  void insertRecordsIntoCheckingTable() {
    CheckingModel bankingAccountModel =
        CheckingModel.builder()
            .transactionDescription("")
            .transactionDate(Date.valueOf("2024-10-10"))
            .transactionType("")
            .transactionAmount(BigDecimal.valueOf(0.0))
            .balance(BigDecimal.valueOf(0.0))
            .build();
    final Map<String, Object> params = new HashMap<>();
    params.put("transactionDescription", bankingAccountModel.getTransactionDescription());
    params.put("transactionDate", bankingAccountModel.getTransactionDate());
    params.put("transactionType", bankingAccountModel.getTransactionType());
    params.put("transactionAmount", bankingAccountModel.getTransactionAmount());
    params.put("balance", bankingAccountModel.getBalance());
    Mockito.when(
            namedParameterJdbcTemplate.update(
                sqlQueriesConfig.getMap().get(INSERT_CHECKING_RECORDS), params))
        .thenReturn(1);
    Assertions.assertDoesNotThrow(
        () -> insertCheckingRecords.insertCheckingRecords(bankingAccountModel));
    Mockito.verify(namedParameterJdbcTemplate, Mockito.times(1))
        .update(sqlQueriesConfig.getMap().get(INSERT_CHECKING_RECORDS), params);
  }

  @Test
  @DisplayName("insert records into credit table")
  void insertRecordsIntoCreditTable() {
    CreditModel creditCardRecordsModel =
        CreditModel.builder()
            .transactionAmount(BigDecimal.valueOf(0.0))
            .transactionType("")
            .transactionCategory("")
            .transactionDescription("")
            .transactionDate(Date.valueOf("2024-10-10"))
            .build();
    final Map<String, Object> params = new HashMap<>();
    params.put("transactionDate", creditCardRecordsModel.getTransactionDate());
    params.put("transactionDescription", creditCardRecordsModel.getTransactionDescription());
    params.put("transactionCategory", creditCardRecordsModel.getTransactionCategory());
    params.put("transactionType", creditCardRecordsModel.getTransactionType());
    params.put("transactionAmount", creditCardRecordsModel.getTransactionAmount());
    Mockito.when(
            namedParameterJdbcTemplate.update(
                sqlQueriesConfig.getMap().get(INSERT_INTO_CREDIT_TABLE), params))
        .thenReturn(1);
    Assertions.assertDoesNotThrow(
        () -> insertCreditRecords.insertCreditRecords(creditCardRecordsModel));
    Mockito.verify(namedParameterJdbcTemplate, Mockito.times(1))
        .update(sqlQueriesConfig.getMap().get(INSERT_INTO_CREDIT_TABLE), params);
  }

  private Properties loadYamlProperties() {
    final Resource resource = new ClassPathResource("queries.yml");
    final YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
    yamlPropertiesFactoryBean.setResources(resource);
    return yamlPropertiesFactoryBean.getObject();
  }
}
