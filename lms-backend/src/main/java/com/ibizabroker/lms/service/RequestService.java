package com.ibizabroker.lms.service;
import com.ibizabroker.lms.dao.RequestedBookRepository;
import com.ibizabroker.lms.dao.UsersRepository;
import com.ibizabroker.lms.dao.RoleRepository;
import com.ibizabroker.lms.dao.BorrowRepository;
import com.ibizabroker.lms.entity.Role;
import com.ibizabroker.lms.entity.Users;
import com.ibizabroker.lms.enums.ReturnStatus;
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
        return borrowRepository.countByReturnRequestStatus(ReturnStatus.PENDING);
    }
}

