package com.proud.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proud.dao.ConsumerDao;
import com.proud.entity.ConsumerEntity;
import org.springframework.stereotype.Service;

/**
 * @author
 * @version 1.0
 * @date 2022/6/10 22:48
 */
@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerDao, ConsumerEntity> implements ConsumerService {
}
