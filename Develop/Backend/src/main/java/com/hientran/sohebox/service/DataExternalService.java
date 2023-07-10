package com.hientran.sohebox.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hientran.sohebox.constants.DataExternalConstants;
import com.hientran.sohebox.dto.PageResultVO;
import com.hientran.sohebox.dto.SjcGoldCityVO;
import com.hientran.sohebox.dto.SjcGoldItemVO;
import com.hientran.sohebox.dto.SjcGoldVO;
import com.hientran.sohebox.dto.VcbCurrencyItemVO;
import com.hientran.sohebox.dto.VcbCurrencyVO;
import com.hientran.sohebox.dto.response.APIResponse;
import com.hientran.sohebox.dto.response.ResponseCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataExternalService extends BaseService {

	@Value("${resource.path}")
	private String resourcePath;

	/**
	 * Get vietcombank foreigner rate
	 *
	 */
	public APIResponse<Object> getVietcombankForeignerRate() {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();
		String filePath = resourcePath + DataExternalConstants.REQUEST_DATA_FILE_PATH_VCB;

		// Check and update file
		try {
			refreshExternalFile(filePath);
		} catch (Exception e) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
		}

		// Get data from a file
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(filePath));

			if (document != null) {
				// Transform result data list
				VcbCurrencyVO resultItem = new VcbCurrencyVO();
				List<VcbCurrencyItemVO> rates = new ArrayList<>();

				NodeList nodeList = document.getDocumentElement().getChildNodes();
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node currentNode = nodeList.item(i);
					if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
						switch (currentNode.getNodeName()) {
						case "DateTime":
							resultItem.setDateTime(currentNode.getTextContent());
							break;
						case "Exrate":
							Element elementExrate = (Element) currentNode;
							VcbCurrencyItemVO rate = new VcbCurrencyItemVO();
							rate.setCurrencyCode(elementExrate.getAttribute("CurrencyCode"));
							rate.setCurrencyName(elementExrate.getAttribute("CurrencyName"));
							rate.setBuy(elementExrate.getAttribute("Buy"));
							rate.setTransfer(elementExrate.getAttribute("Transfer"));
							rate.setSell(elementExrate.getAttribute("Sell"));
							rates.add(rate);
							break;
						default:
							break;
						}
					}
				}
				resultItem.setRates(rates);

				// Set return data
				List<VcbCurrencyVO> listItem = new ArrayList<>();
				listItem.add(resultItem);

				PageResultVO<VcbCurrencyVO> data = new PageResultVO<>();
				data.setElements(listItem);
				data.setCurrentPage(0);
				data.setTotalPage(1);
				data.setTotalElement(listItem.size());

				// Set data return
				result.setData(data);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Return
		return result;
	}

	/**
	 * Get SJC gold price
	 *
	 */
	public APIResponse<Object> getSjcGoldPrice() {
		// Declare result
		APIResponse<Object> result = new APIResponse<>();
		String filePath = resourcePath + DataExternalConstants.REQUEST_DATA_FILE_PATH_SJC;

		// Check and update file
		try {
			refreshExternalFile(filePath);
		} catch (Exception e) {
			result = new APIResponse<>(HttpStatus.BAD_REQUEST,
					ResponseCode.mapParam(ResponseCode.ERROR_EXCEPTION, e.getMessage()));
		}

		// Get data from a file
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(filePath));

			if (document != null) {
				// Transform result data list
				SjcGoldVO resultData = new SjcGoldVO();
				NodeList nodeList = document.getDocumentElement().getChildNodes();
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node currentNode = nodeList.item(i);
					if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
						switch (currentNode.getNodeName()) {
						case "title":
							resultData.setTitle(currentNode.getTextContent());
							break;
						case "url":
							resultData.setUrl(currentNode.getTextContent());
							break;
						case "ratelist":
							Element elementRate = (Element) currentNode;
							resultData.setUpdated(elementRate.getAttribute("updated"));
							resultData.setUnit(elementRate.getAttribute("unit"));
							resultData.setCity(getGoldbyCity(currentNode));
							break;
						default:
							break;
						}
					}
				}

				// Set return data
				List<SjcGoldVO> listData = new ArrayList<>();
				listData.add(resultData);

				PageResultVO<SjcGoldVO> data = new PageResultVO<>();
				data.setElements(listData);
				data.setCurrentPage(0);
				data.setTotalPage(1);
				data.setTotalElement(listData.size());

				// Set data return
				result.setData(data);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Return
		return result;
	}

	/**
	 * SJC Gold: Transform node city to object
	 *
	 */
	private List<SjcGoldCityVO> getGoldbyCity(Node node) {
		// Declare result
		List<SjcGoldCityVO> result = new ArrayList<>();

		// Get data
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				Element elementCity = (Element) currentNode;
				SjcGoldCityVO city = new SjcGoldCityVO();
				city.setCityName(elementCity.getAttribute("name"));
				city.setItems(getCityItem(currentNode));
				result.add(city);
			}
		}

		// Return
		return result;
	}

	/**
	 * SJC Gold: Transform node item to object
	 *
	 */
	private List<SjcGoldItemVO> getCityItem(Node node) {
		// Declare result
		List<SjcGoldItemVO> result = new ArrayList<>();

		// Get data
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) currentNode;
				SjcGoldItemVO item = new SjcGoldItemVO();
				item.setBuy(element.getAttribute("buy"));
				item.setSell(element.getAttribute("sell"));
				item.setType(element.getAttribute("type"));
				result.add(item);
			}
		}

		// Return
		return result;
	}
}
