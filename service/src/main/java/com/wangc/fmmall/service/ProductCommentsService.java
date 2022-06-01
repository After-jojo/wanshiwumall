package com.wangc.fmmall.service;

import com.wangc.fmmall.vo.ResultVO;

public interface ProductCommentsService {
    public ResultVO listCommontsByProId(String pid, int start, int limit);

    public ResultVO getCommCountByProId(String pid);
}
