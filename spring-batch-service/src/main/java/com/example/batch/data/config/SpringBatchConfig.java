package com.example.batch.data.config;

import com.example.batch.data.entity.BitcoinData;
import com.example.batch.data.repository.BitcoinDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private BitcoinDataRepository bitcoinDataRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private FlatFileItemReader<BitcoinData> reader() {
        FlatFileItemReader<BitcoinData> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("btcusd_1-min_data.csv"));
        reader.setLinesToSkip(1);
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("Timestamp","High", "Low", "Open", "Close", "Volume");
        DefaultLineMapper<BitcoinData> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSet -> {
            BitcoinData bitcoinData = new BitcoinData();
            bitcoinData.setTimestamp(fieldSet.readString("Timestamp"));
            bitcoinData.setHigh(fieldSet.readString("High"));
            bitcoinData.setLow(fieldSet.readString("Low"));
            bitcoinData.setOpen(fieldSet.readString("Open"));
            bitcoinData.setClose(fieldSet.readString("Close"));
            bitcoinData.setVolume(fieldSet.readString("Volume"));
            return bitcoinData;
        });
        reader.setLineMapper(lineMapper);
        return reader;
    }
    @Bean
    public BitcoinDataProcessor bitcoinDataProcessor() {
        return new BitcoinDataProcessor();
    }

    @Bean
    public MongoItemWriter<BitcoinData> writer() {
        MongoItemWriter<BitcoinData> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("bitcoin_data");
        return writer;
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("csv-step").<BitcoinData, BitcoinData>chunk(10).reader(reader()).writer(writer()).build();
    }

    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("importBitcoin").flow(step()).end().build();
    }

}
