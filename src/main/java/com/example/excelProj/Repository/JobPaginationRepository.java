package com.example.excelProj.Repository;

import com.example.excelProj.Model.Job;
//import jdk.nashorn.internal.scripts.JO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by Rehan on 3/22/2020.
 */
@Repository
public interface  JobPaginationRepository extends PagingAndSortingRepository<Job,Long>{



}
