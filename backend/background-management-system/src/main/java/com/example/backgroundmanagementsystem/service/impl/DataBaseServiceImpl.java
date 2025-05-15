package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.service.DataBaseService;
import com.example.backgroundmanagementsystem.utils.DataBaseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataBaseServiceImpl implements DataBaseService {
    private final DataBaseUtils dataBaseUtils;

    @Override
    public void test() {
        dataBaseUtils.dbBackUp();
    }
}
