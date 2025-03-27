package com.spring.JspringProject.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.JspringProject.common.ProjectProvide;
import com.spring.JspringProject.dao.PdsDao;
import com.spring.JspringProject.vo.PdsVo;

@Service
public class PdsServiceImpl implements PdsService {

	
	@Autowired
	PdsDao pdsDao;

	@Autowired
	ProjectProvide projectProvide;

	@Override
	public List<PdsVo> getPdsList(int startIndexNo, int pageSize, String part, String search, String searchString) {
		return pdsDao.getPdsList(startIndexNo, pageSize, part, search, searchString);
	}

	@Override
	public int setPdsInput(MultipartHttpServletRequest mFName, PdsVo vo) { //파일업로드처리
		// 파일 업로드 처리
		try {
			List<MultipartFile> fileList = mFName.getFiles("file");
			String oFileNames = "";
			String sFileNames = "";
			int fileSizes = 0;
			
			for(MultipartFile file : fileList) {
				String oFileName = file.getOriginalFilename();
				String sFileName = projectProvide.saveFileName(oFileName);
				projectProvide.writeFile(file, sFileName, "pds");
				
				oFileNames += oFileName + "/";
				sFileNames += sFileName + "/";
				fileSizes += file.getSize();
			}
			oFileNames = oFileNames.substring(0, oFileNames.length()-1);
			sFileNames = sFileNames.substring(0, sFileNames.length()-1);

			vo.setFName(oFileNames);
			vo.setFSName(sFileNames);
			vo.setFSize(fileSizes);
			
		} catch (IOException e) { e.printStackTrace();}
			// 데이터베이스에 저장하기
		return pdsDao.setPdsInput(vo); // DB처리
	}

	@Override
	public int setPdsDelete(int idx, String fSName ,PdsVo vo) {
		try {
			List<MultipartFile> fileList = mFName.getFiles("file");
			String oFileNames = "";
			String sFileNames = "";
			int fileSizes = 0;
			
			for(MultipartFile file : fileList) {
				String oFileName = file.getOriginalFilename();
				String sFileName = projectProvide.saveFileName(oFileName);
				projectProvide.writeFile(file, sFileName, "pds");
				
				oFileNames += oFileName + "/";
				sFileNames += sFileName + "/";
				fileSizes += file.getSize();
			}
			oFileNames = oFileNames.substring(0, oFileNames.length()-1);
			sFileNames = sFileNames.substring(0, sFileNames.length()-1);

			vo.setFName(oFileNames);
			vo.setFSName(sFileNames);
			vo.setFSize(fileSizes);
			
		return pdsDao.setPdsDelete(idx ,vo);
	}

	@Override
	public List<File> getPdsFiles(String files) {
		return pdsDao.getPdsFiles(files);
	}
}
