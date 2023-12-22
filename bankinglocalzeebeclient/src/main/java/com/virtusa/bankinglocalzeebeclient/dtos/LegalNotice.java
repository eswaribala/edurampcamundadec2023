package com.virtusa.bankinglocalzeebeclient.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalNotice {
	private String noticeNo;
	private String issuingAuthority;
	private String description;
	private LocalDate expiryDate;

}
