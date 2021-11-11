package com.snaker.dfs.service;

import com.snaker.dfs.domain.DfsFile;
import com.snaker.dfs.domain.ZipDfsFile;

import java.util.List;

/**
 * 业务 服务层
 *
 * @author sfd
 */
public interface IDfsFileService {
    public List<DfsFile> selectDfsFileList(DfsFile dfsFile);

    /**
     * 新增业务
     *
     * @param genTable 业务信息
     * @return 结果
     */
    public int insertDfsFile(DfsFile dfsFile);

    int insertZipDfsFile(ZipDfsFile zipDfsFile);

    /**
     * 修改业务
     *
     * @param dfsFile 业务信息
     * @return 结果
     */
    public int updateDfsFile(DfsFile dfsFile);

    /**
     * 批量删除业务
     *
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteDfsFileByIds(String id);

    public DfsFile selectDfsFileById(String id);

    public List<DfsFile> selectDfsFileListByIds(String ids);

    String fetchPdfFileIdByOriginOfficeFileId(String originOfficeFileId);

    void richPdfFileId(DfsFile dfsFile);

    String getPdfByOriginOfficeFileId(String originOfficeFileId, String sessionTempFilePath) throws Exception;

    void asynStartBuildPdfFile(String originOfficeFileId, String sessionTempFilePath) throws Exception;

    Integer delDfsFilePdfRef(String id);

    ZipDfsFile selectZipDfsFileById(String id);
}
