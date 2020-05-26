package com.discord.bot.utils;

public class Paging {

	private int totalCnt;		//전체 게시글 수
	private int currPage;		//현재 페이지
	private int totalPage;		//전체 페이지
	private int pageSize = 10;	//한 페이지당 글 수
	private int startRow;		//시작 게시물 번호
	private int endRow;			//마지막 게시글 번호

	public Paging(int currPage, int totalCnt) {
		this.totalCnt = totalCnt;
		this.currPage = currPage;
		setTotalPage(totalCnt);
		setStartRow();
		setEndRow();
	}

	public void setTotalPage(int totalCnt) {	// 총 게시글 수로 전체 페이지 수 계산
		this.totalPage = (int) Math.ceil(totalCnt * 1.0 / pageSize);
	}

	public void setStartRow(){
		this.startRow = this.currPage * this.pageSize - (this.pageSize - 1);
	}

	public void setEndRow(){
		this.endRow = this.startRow + this.pageSize - 1;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public int getEndRow() {
		return endRow;
	}

	@Override
	public String toString(){
		return "Page "+ this.currPage + "/" + this.totalPage;
	}
}
