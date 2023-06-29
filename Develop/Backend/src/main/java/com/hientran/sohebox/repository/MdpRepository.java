package com.hientran.sohebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hientran.sohebox.entity.MdpTbl;

public interface MdpRepository extends JpaRepository<MdpTbl, Long>, JpaSpecificationExecutor<MdpTbl> {
}