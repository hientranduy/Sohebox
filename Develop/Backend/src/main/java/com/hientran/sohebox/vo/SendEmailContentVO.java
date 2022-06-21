package com.hientran.sohebox.vo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * Mail content
 *
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class SendEmailContentVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String mailFrom;

    private String[] listMailTo;

    private String subject;

    private String textBody;

    private String templatePath;

    private Map<String, String> parameters;

    private List<String> pathToAttachments;

    /**
     * Constructor
     *
     */
    public SendEmailContentVO() {
        super();
    }

    /**
     * Get mailFrom
     *
     * @return mailFrom
     */
    public String getMailFrom() {
        return mailFrom;
    }

    /**
     * Set mailFrom
     *
     * @param mailFrom
     *            the mailFrom to set
     */
    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    /**
     * Get listMailTo
     *
     * @return listMailTo
     */
    public String[] getListMailTo() {
        return listMailTo;
    }

    /**
     * Set listMailTo
     *
     * @param listMailTo
     *            the listMailTo to set
     */
    public void setListMailTo(String[] listMailTo) {
        this.listMailTo = listMailTo;
    }

    /**
     * Get subject
     *
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set subject
     *
     * @param subject
     *            the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Get textBody
     *
     * @return textBody
     */
    public String getTextBody() {
        return textBody;
    }

    /**
     * Set textBody
     *
     * @param textBody
     *            the textBody to set
     */
    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    /**
     * Get templatePath
     *
     * @return templatePath
     */
    public String getTemplatePath() {
        return templatePath;
    }

    /**
     * Set templatePath
     *
     * @param templatePath
     *            the templatePath to set
     */
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * Get parameters
     *
     * @return parameters
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Set parameters
     *
     * @param parameters
     *            the parameters to set
     */
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    /**
     * Get pathToAttachments
     *
     * @return pathToAttachments
     */
    public List<String> getPathToAttachments() {
        return pathToAttachments;
    }

    /**
     * Set pathToAttachments
     *
     * @param pathToAttachments
     *            the pathToAttachments to set
     */
    public void setPathToAttachments(List<String> pathToAttachments) {
        this.pathToAttachments = pathToAttachments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SendEmailContentVO [mailFrom=" + mailFrom + ", listMailTo=" + Arrays.toString(listMailTo) + ", subject="
                + subject + ", textBody=" + textBody + ", templatePath=" + templatePath + ", parameters=" + parameters
                + ", pathToAttachments=" + pathToAttachments + "]";
    }

}