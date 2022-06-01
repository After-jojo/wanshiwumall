package com.wangc.fmmall.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "product_comments")
public class ProductCommentsVO {

    private String commId;


    private String productId;


    private String productName;


    private String orderItemId;


    private String userId;


    private Integer isAnonymous;


    private Integer commType;


    private Integer commLevel;


    private String commContent;


    private String commImgs;


    private Date sepcName;


    private Integer replyStatus;


    private String replyContent;


    private Date replyTime;


    private Integer isShow;

    private String username;
    private String nickname;
    private String userImg;

    public ProductCommentsVO() {
    }

    public ProductCommentsVO(String commId, String productId, String productName, String orderItemId, String userId, Integer isAnonymous, Integer commType, Integer commLevel, String commContent, String commImgs, Date sepcName, Integer replyStatus, String replyContent, Date replyTime, Integer isShow, String username, String nickname, String userImg) {
        this.commId = commId;
        this.productId = productId;
        this.productName = productName;
        this.orderItemId = orderItemId;
        this.userId = userId;
        this.isAnonymous = isAnonymous;
        this.commType = commType;
        this.commLevel = commLevel;
        this.commContent = commContent;
        this.commImgs = commImgs;
        this.sepcName = sepcName;
        this.replyStatus = replyStatus;
        this.replyContent = replyContent;
        this.replyTime = replyTime;
        this.isShow = isShow;
        this.username = username;
        this.nickname = nickname;
        this.userImg = userImg;
    }

    public String getCommId() {
        return commId;
    }

    /**
     * 设置ID
     *
     * @param commId ID
     */
    public void setCommId(String commId) {
        this.commId = commId;
    }

    /**
     * 获取商品id
     *
     * @return product_id - 商品id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置商品id
     *
     * @param productId 商品id
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 获取商品名称
     *
     * @return product_name - 商品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置商品名称
     *
     * @param productName 商品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取订单项(商品快照)ID 可为空
     *
     * @return order_item_id - 订单项(商品快照)ID 可为空
     */
    public String getOrderItemId() {
        return orderItemId;
    }

    /**
     * 设置订单项(商品快照)ID 可为空
     *
     * @param orderItemId 订单项(商品快照)ID 可为空
     */
    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    /**
     * 获取评论用户id 用户名须脱敏
     *
     * @return user_id - 评论用户id 用户名须脱敏
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置评论用户id 用户名须脱敏
     *
     * @param userId 评论用户id 用户名须脱敏
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取是否匿名（1:是，0:否）
     *
     * @return is_anonymous - 是否匿名（1:是，0:否）
     */
    public Integer getIsAnonymous() {
        return isAnonymous;
    }

    /**
     * 设置是否匿名（1:是，0:否）
     *
     * @param isAnonymous 是否匿名（1:是，0:否）
     */
    public void setIsAnonymous(Integer isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    /**
     * 获取评价类型（1好评，0中评，-1差评）
     *
     * @return comm_type - 评价类型（1好评，0中评，-1差评）
     */
    public Integer getCommType() {
        return commType;
    }

    /**
     * 设置评价类型（1好评，0中评，-1差评）
     *
     * @param commType 评价类型（1好评，0中评，-1差评）
     */
    public void setCommType(Integer commType) {
        this.commType = commType;
    }

    /**
     * 获取评价等级 1：好评 2：中评 3：差评
     *
     * @return comm_level - 评价等级 1：好评 2：中评 3：差评
     */
    public Integer getCommLevel() {
        return commLevel;
    }

    /**
     * 设置评价等级 1：好评 2：中评 3：差评
     *
     * @param commLevel 评价等级 1：好评 2：中评 3：差评
     */
    public void setCommLevel(Integer commLevel) {
        this.commLevel = commLevel;
    }

    /**
     * 获取评价内容
     *
     * @return comm_content - 评价内容
     */
    public String getCommContent() {
        return commContent;
    }

    /**
     * 设置评价内容
     *
     * @param commContent 评价内容
     */
    public void setCommContent(String commContent) {
        this.commContent = commContent;
    }

    /**
     * 获取评价晒图(JSON {img1:url1,img2:url2}  )
     *
     * @return comm_imgs - 评价晒图(JSON {img1:url1,img2:url2}  )
     */
    public String getCommImgs() {
        return commImgs;
    }

    /**
     * 设置评价晒图(JSON {img1:url1,img2:url2}  )
     *
     * @param commImgs 评价晒图(JSON {img1:url1,img2:url2}  )
     */
    public void setCommImgs(String commImgs) {
        this.commImgs = commImgs;
    }

    /**
     * 获取评价时间 可为空
     *
     * @return sepc_name - 评价时间 可为空
     */
    public Date getSepcName() {
        return sepcName;
    }

    /**
     * 设置评价时间 可为空
     *
     * @param sepcName 评价时间 可为空
     */
    public void setSepcName(Date sepcName) {
        this.sepcName = sepcName;
    }

    /**
     * 获取是否回复（0:未回复，1:已回复）
     *
     * @return reply_status - 是否回复（0:未回复，1:已回复）
     */
    public Integer getReplyStatus() {
        return replyStatus;
    }

    /**
     * 设置是否回复（0:未回复，1:已回复）
     *
     * @param replyStatus 是否回复（0:未回复，1:已回复）
     */
    public void setReplyStatus(Integer replyStatus) {
        this.replyStatus = replyStatus;
    }

    /**
     * 获取回复内容
     *
     * @return reply_content - 回复内容
     */
    public String getReplyContent() {
        return replyContent;
    }

    /**
     * 设置回复内容
     *
     * @param replyContent 回复内容
     */
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    /**
     * 获取是否显示（1:是，0:否）
     *
     * @return reply_time - 是否显示（1:是，0:否）
     */
    public Date getReplyTime() {
        return replyTime;
    }

    /**
     * 设置是否显示（1:是，0:否）
     *
     * @param replyTime 是否显示（1:是，0:否）
     */
    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    /**
     * 获取是否显示（1:是，0:否）
     *
     * @return is_show - 是否显示（1:是，0:否）
     */
    public Integer getIsShow() {
        return isShow;
    }

    /**
     * 设置是否显示（1:是，0:否）
     *
     * @param isShow 是否显示（1:是，0:否）
     */
    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    @Override
    public String toString() {
        return "ProductCommentsVO{" +
                "commId='" + commId + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", orderItemId='" + orderItemId + '\'' +
                ", userId='" + userId + '\'' +
                ", isAnonymous=" + isAnonymous +
                ", commType=" + commType +
                ", commLevel=" + commLevel +
                ", commContent='" + commContent + '\'' +
                ", commImgs='" + commImgs + '\'' +
                ", sepcName=" + sepcName +
                ", replyStatus=" + replyStatus +
                ", replyContent='" + replyContent + '\'' +
                ", replyTime=" + replyTime +
                ", isShow=" + isShow +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userImg='" + userImg + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }


}