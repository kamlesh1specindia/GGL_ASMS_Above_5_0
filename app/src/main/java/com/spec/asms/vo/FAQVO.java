package com.spec.asms.vo;

import com.spec.asms.common.Constants;

public class FAQVO {
	
	private int id;
	private String question = Constants.LABEL_BLANK;
	private String answers = Constants.LABEL_BLANK;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	
	
}
