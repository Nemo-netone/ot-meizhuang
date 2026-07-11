package com.example.entity;

import javax.persistence.*;
import java.util.List;

@Table(name = "shop_info")
public class ShopInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "name")
	private String name;
	@Column(name = "tel")
	private String tel;
	@Column(name = "content")
	private String content;
	@Column(name = "time")
	private String time;
	@Transient
	private List<Long> fileList;
	/**
	 * 商品图片id
	 */
	@Column(name = "fileIds")
	private String fileIds;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFileId() {
		return fileIds;
	}
	public void setFileId(String fileIds) {
		this.fileIds = fileIds;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<Long> getFileList() {
		return fileList;
	}

	public void setFileList(List<Long> fileList) {
		this.fileList = fileList;
	}
	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return this.id;
    }

}
