package com.snaker.system.service;

//import com.snaker.system.domain.DfsFileVersion;
//import com.snaker.system.domain.DmBusinessFileRes;
//import com.snaker.system.domain.SysApprove;

import javax.servlet.http.HttpServletResponse;
        import java.util.List;

public interface ISysFileService {

     void getFileByUrl(String url, HttpServletResponse response) throws Throwable;


    public List<String> readExcelTemplate(String path);

//    List<DfsFile> batchGetDfsFileByFileIds(Set<String> fileIds) throws Throwable;
//
//    Map<String, Object> batchSaveFileToDfs(File file, long currentUserId, String fileCategoryId) throws Throwable;
//
//    @Transactional(rollbackFor = Exception.class)
//    Map<String, Object> batchSaveFileToDfs(List<File> files, long currentUserId, String fileCategoryId) throws Throwable;
//
//    Map<String,Object> batchSaveFileToDfsWithoutTransactional(List<File> files, long currentUserId, String fileCategoryId) throws Throwable ;
//    void batchSaveFileToDBWithoutTransactional(List<DfsFile> dfsFiles, String fileCategoryId);
//
//    void batchSaveFileToDBWithoutTransactional(Set<String> fileIds, String fileCategoryId, String currentUserId);
//
//    /**
//     * 修改文档不提交审批
//     *
//     * @param dfsFile
//     * @return
//     */
//    List<DfsFile> getCategoryFileList(DfsFile dfsFile);
//
//    /**
//     * 修改文档不提交审批
//     *
//     * @param file
//     * @param dfsFile
//     * @param sourceFile
//     * @param currentUserIdString
//     * @return
//     * @throws Throwable
//     */
//    int updateDmFile(MultipartFile file, DfsFile dfsFile, MultipartFile sourceFile, String currentUserIdString) throws Throwable;
//
//    /**
//     * 修改文档并提交审批
//     *
//     * @param file
//     * @param dfsFile
//     * @param sourceFile
//     * @param currentUserId
//     * @return
//     * @throws Throwable
//     */
//    int sumbitDmFile(MultipartFile file, DfsFile dfsFile, MultipartFile sourceFile, long currentUserId) throws Throwable;
//
//    Boolean sendApprove(String id, String userId) throws Throwable;
//
//    public int handleApprove(SysApprove sysApprove);
//
//    /**
//     * 获取文档历史版本列表
//     *
//     * @param id
//     * @return
//     */
//    List<DfsFileVersion> getDmFileVersionList(String id);
//
//    void fileIdToFileConvert(List list) throws Throwable;
//
//    /**
//     * 批量删除文档 服务器上文件、业务关联关系、历史版本均删除，文档本身状态置为已删除
//     *
//     * @param ids
//     * @param paths
//     * @return
//     * @throws Throwable
//     */
//    int deleteDmfilesByIds(String ids, String paths) throws Throwable;
//
//    @Transactional
//    int deleteDmfilesByIds(String fileIds) throws Throwable;
//
//    /**
//     * 业务数据审批通过，批量将审批中的文件置为生效
//     *
//     * @param fileIds
//     * @return
//     * @throws Throwable
//     */
//    int batchUpdateDfsFileToDelFlag0(String fileIds) throws Throwable;
//
//    /**
//     * 业务数据审批未通过，批量将审批中的文件置为待发起
//     *
//     * @param fileIds
//     * @return
//     * @throws Throwable
//     */
//    int batchUpdateDfsFileToDelFlag2(String fileIds) throws Throwable;
//
//    int batchUpdateDfsFileToDelFlag(String fileIds, String delFlag);
//
//    int delDmBusinessFileResByBusinessId(Integer modal, String businessId);
//
//    void batchInsertDmBusinessFileRes(List<DmBusinessFileRes> list);
//
//    int batchDelTmDfsFiles(String fileIds) throws Throwable;
//
//    /**
//     * 获取引用文档模块记录列表
//     *
//     * @param fileId
//     * @return
//     */
//    List<DmBusinessFileRes> getDmBusinessFilResList(String fileId);
//
//    /**
//     * 删除文档历史版本
//     *
//     * @param id
//     * @param path
//     * @param fileId
//     * @return
//     * @throws Throwable
//     */
//    int deleteDmfileVersion(String id, String path, String sourceFilePath, String fileId) throws Throwable;
//
//    @Transactional
//    int deleteDmBusinessFileResList(List<DmBusinessFileRes> list);
//
//    int uploadDmSourceFile(MultipartFile file, DfsFile dfsFile, long currentUserId) throws Throwable;
//
//    /**
//     * 保存包含源文件的文件（传入的为文件夹）
//     *
//     * @param file
//     * @param currentUserId
//     * @param fileCategoryId
//     * @return
//     * @throws Throwable
//     */
//    Map<String, Object> saveContainSourceFile(File file, long currentUserId, String fileCategoryId) throws Throwable;
//
//    DfsFile uploadFile(File uploadFile, Long currentUserId, String delFlag, String md5) throws Throwable;
//
//    ZipDfsFile uploadDirFileAndBuildDirTreeAndZipFile(File dirFile, Long currentUserId) throws Throwable;
//
//    String uploadBigFile(Chunk chunk, HttpServletResponse response) throws Throwable;
//
//    void downloadZip(String ids, ByteArrayOutputStream bt) throws Throwable;
}
