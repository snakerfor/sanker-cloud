package com.snaker.dfs.mapper;

import com.snaker.dfs.domain.DfsFile;
import com.snaker.dfs.domain.ZipDfsFile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 业务 数据层
 *
 * @author sfd
 */
public interface DfsFileMapper {

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
     * @param genTable 业务信息
     * @return 结果
     */
    public int updateDfsFile(DfsFile dfsFile);

    /**
     * 批量删除业务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDfsFileByIds(String[] ids);


    public DfsFile selectDfsFileById(String id);

    public List<DfsFile> selectDfsFileListByIds(String[] ids);

    @Select("select pdf_id from dfs_file_pdf_ref where id = #{originOfficeFileId} ")
    String getPdfFileIdByOriginOfficeFileId(@Param("originOfficeFileId") String originOfficeFileId);

    @Insert("insert into dfs_file_pdf_ref (id,pdf_id) values( #{originOfficeFileId} , #{pdfId} )")
    Integer insertDfsFilePdfRef(@Param("originOfficeFileId") String originOfficeFileId, @Param("pdfId") String pdfId);

    @Delete("DELETE FROM dfs_file_pdf_ref WHERE id=#{id}")
    Integer delDfsFilePdfRef(@Param("id") String id);

    Integer batchUpdateDfsFileDelFlagByOldDelFlags(DfsFile dfsFile);

    ZipDfsFile selectZipDfsFileById(String id);
}
