package com.mine.product.szmtr.msgboard.faqs.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mine.product.szmtr.msgboard.faqs.model.FaqsModel;

public interface IFaqsDao extends JpaRepository<FaqsModel, String>{
}
