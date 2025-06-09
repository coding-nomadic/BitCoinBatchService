package com.example.batch.data.config;

import com.example.batch.data.entity.BitcoinData;
import org.springframework.batch.item.ItemProcessor;


public class BitcoinDataProcessor implements ItemProcessor<BitcoinData, BitcoinData> {

    @Override
    public BitcoinData process(BitcoinData bitcoinData) throws Exception {
        return new BitcoinData();
    }
}
