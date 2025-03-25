package com.ibizabroker.lms.service;
import com.ibizabroker.lms.dao.RequestedBookRepository;
import com.ibizabroker.lms.dao.BorrowRepository;
import com.ibizabroker.lms.enums.ReturnReqStatus;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestedBookRepository requestedBookRepository;
    private final BorrowRepository borrowRepository;

    public long getTotalRequestedBooks() {
        return requestedBookRepository.count();
    }

    public long getTotalReturnRequests() {
        return borrowRepository.countByReturnRequestStatus(ReturnReqStatus.PENDING);
    }
}

