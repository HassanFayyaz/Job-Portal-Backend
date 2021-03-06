package com.example.excelProj.Service;

import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Dto.JobDTO;
import com.example.excelProj.Dto.JobPostDTO;
import com.example.excelProj.Dto.ReviewAndRatingDTO;
import com.example.excelProj.Model.*;
import com.example.excelProj.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    UserDaoRepository userDaoRepository;

    @Autowired
    JobPaginationRepository jobPaginationRepository;

    @Autowired
    CandidateProfileRepository candidateProfileRepository;


    @Autowired
    ReviewAndRatingRepository reviewAndRatingRepository;

    @Autowired
    AppliedForRepository appliedForRepository;


    @Autowired
    CompanyProfileRepository companyProfileRepository;
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

//    Paginated jobs


    public Page<Job> getPaginatedJobs(int pageNumber){
        Pageable firstPageWithTenElements = PageRequest.of(pageNumber, 10);
        return jobPaginationRepository.findAll(firstPageWithTenElements);
    }


    public ApiResponse getAJob(Long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);

        if (optionalJob.isPresent()) {
            return new ApiResponse(200, "Job fetching successful", optionalJob.get());
        }
        return new ApiResponse(500, "Error fetching the job", null);
    }


    public ApiResponse postJob(JobPostDTO jobDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userDaoRepository.findByEmail(currentPrincipalName);


        if (user != null && !user.getUserType().equalsIgnoreCase("candidate") && user.getCompanyProfile() != null) {

            Job job = new Job();

            job.setDescription(jobDTO.getDescription());
            job.setSalary(jobDTO.getSalary());
            job.setLatitude(jobDTO.getLatitude());
            job.setLongitude(jobDTO.getLongitude());
            job.setTitle(jobDTO.getTitle());
            job.setCity(jobDTO.getCity());
            job.setCountry(jobDTO.getCountry());
            job.setProvince(jobDTO.getProvince());
            job.setCategory(jobDTO.getCategory());
            job.setType(jobDTO.getType());
            job.setPublishFrom(jobDTO.getPublishFrom());
            job.setAddress(jobDTO.getAddress());
            job.setPublishTo(jobDTO.getPublishTo());
            job.setCompanyProfile(user.getCompanyProfile());
            job.setDate(new Date());
            job.setJobPostPermission(true);
            return new ApiResponse(200, "Job successfully posted", jobRepository.save(job));
        }

        else if(user!=null && user.getUserType().equalsIgnoreCase("recruiter")){

                Job job = new Job();
                job.setDescription(jobDTO.getDescription());
                job.setSalary(jobDTO.getSalary());
                job.setLatitude(jobDTO.getLatitude());
                job.setLongitude(jobDTO.getLongitude());
                job.setTitle(jobDTO.getTitle());
                job.setCity(jobDTO.getCity());
                job.setCountry(jobDTO.getCountry());
                job.setProvince(jobDTO.getProvince());
                job.setCategory(jobDTO.getCategory());
                job.setType(jobDTO.getType());
                job.setAddress(jobDTO.getAddress());
                job.setPublishFrom(jobDTO.getPublishFrom());
                job.setPublishTo(jobDTO.getPublishTo());
                job.setCompanyProfile(user.getCompanyProfile());
                job.setDate(new Date());
            job.setJobPostPermission(true);

                return new ApiResponse(200, "Job posted succesffully by recruiter", jobRepository.save(job));
            }



        return new ApiResponse(500, "Something went wrong", null);
    }


//    UPDATE JOB

    public ApiResponse updateJOB(Long jobId,JobPostDTO jobDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userDaoRepository.findByEmail(currentPrincipalName);


//        job post for company
        if (user != null && user.getUserType().equalsIgnoreCase("employer") && user.getCompanyProfile() != null) {

            Optional<Job> jobOptional = jobRepository.findById(jobId);
            if(jobOptional.isPresent()){

                Job job = jobOptional.get();
                job.setDescription(jobDTO.getDescription());
                job.setSalary(jobDTO.getSalary());
                job.setLatitude(jobDTO.getLatitude());
                job.setLongitude(jobDTO.getLongitude());
                job.setTitle(jobDTO.getTitle());
                job.setCity(jobDTO.getCity());
                job.setCountry(jobDTO.getCountry());
                job.setProvince(jobDTO.getProvince());
                job.setCategory(jobDTO.getCategory());
                job.setType(jobDTO.getType());
                job.setAddress(jobDTO.getAddress());
                job.setPublishFrom(jobDTO.getPublishFrom());
                job.setPublishTo(jobDTO.getPublishTo());
                job.setCompanyProfile(user.getCompanyProfile());
                job.setDate(new Date());
                job.setJobPostPermission(true);
                return new ApiResponse(200, "Job Updated posted", jobRepository.save(job));
            }


        }
        else if(user!=null && user.getUserType().equalsIgnoreCase("recruiter")){
            Optional<Job> jobOptional = jobRepository.findById(jobId);
            if(jobOptional.isPresent()){

                Job job = jobOptional.get();
                job.setDescription(jobDTO.getDescription());
                job.setSalary(jobDTO.getSalary());
                job.setLatitude(jobDTO.getLatitude());
                job.setLongitude(jobDTO.getLongitude());
                job.setTitle(jobDTO.getTitle());
                job.setCity(jobDTO.getCity());
                job.setCountry(jobDTO.getCountry());
                job.setProvince(jobDTO.getProvince());
                job.setCategory(jobDTO.getCategory());
                job.setType(jobDTO.getType());
                job.setAddress(jobDTO.getAddress());
                job.setPublishFrom(jobDTO.getPublishFrom());
                job.setPublishTo(jobDTO.getPublishTo());

                job.setCompanyProfile(user.getCompanyProfile());
                job.setDate(new Date());
                job.setJobPostPermission(true);
                return new ApiResponse(200, "Job Updated posted", jobRepository.save(job));
            }

        }


        return new ApiResponse(500, "Something went wrong", null);
    }

//    public ApiResponse deleteByJobId(Long id){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//
//        User user = userDaoRepository.findByEmail(currentPrincipalName);
//
//        Boolean jobExists = jobRepository.existsById(id);
//
//        if(jobExists){
//
//            jobRepository.deleteById(id);
//            return new ApiResponse(200, "Job Deleted", jobRepository.findByEmployeeId(user.getCompanyProfile().getId()));
//        }
//        else{
//            return new ApiResponse(500, "Job deleted failed", null);
//
//        }
//
//
//    }





















//
//
//
//    public ApiResponse<Job> apply_on_job(ReviewAndRatingDTO reviewAndRatingDTO){
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        User user = userDaoRepository.findByEmail(currentPrincipalName);
//
//
//
//        if (user != null && user.getUserType().equalsIgnoreCase("candidate") && user.getCandidateProfile()!=null){
//
//            reviewAndRatingDTO.setCandidateId(user.getCandidateProfile().getId());
//            Optional<Job> job = jobRepository.findById(reviewAndRatingDTO.getJobId());
//            CandidateProfile candidateProfile = user.getCandidateProfile();
//
////                reviewAndRatingDTO.setCandidateId(user.getCandidateProfile().getId());
//
//            if(job.isPresent())
//            {
//                AppliedFor appliedFor=new AppliedFor(candidateProfile,job.get(),false,new Date());
//                if(reviewAndRatingDTO.getRating()!=null && reviewAndRatingDTO.getReview()!=null){
//                    if(saveRatingAndReview(reviewAndRatingDTO,user.getUserType())){
//                        return new ApiResponse(200, "Applied on job", jobRepository.save(job.get()));
//                    }
//                    else{
//                        return new ApiResponse(HttpStatus.ALREADY_REPORTED.value(), "You can not give review to the same company twice", jobRepository.save(job.get()));
//                    }
//
//                }
//
//
//                else{
//                    return new ApiResponse(200, "Applied on job without review and rating", appliedForRepository.save(appliedFor));
//                }
//
//            }
//
//        }
//        return new ApiResponse(500, "Something went wrong", null);
//
//    }




    public ApiResponse<Job> getMyJobs(Long employeeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userDaoRepository.findByEmail(currentPrincipalName);

        if (user != null && user.getUserType().equalsIgnoreCase("employer") && user.getCompanyProfile() != null) {

                List<Job> jobList = jobRepository.findByEmployeeId(employeeId);
                return  new ApiResponse(200,"Successfull",jobList);

        }
        else{
            return new ApiResponse(500, "Something went wrong", null);
        }
    }

    public  List<Job> getJobsByCompany(Long id){

       Optional<Job> job=jobRepository.findById(id);
        if(job.isPresent())
        {
            return jobRepository.findByCompanyId(job.get().getCompanyProfile().getId());
        }
        return null;

    }


    public Boolean saveRatingAndReview(ReviewAndRatingDTO reviewAndRatingDTO,String userType){

        Optional<ReviewAndRating> reviewAndRatingObject = reviewAndRatingRepository.findByCandidateIdAndCompanyProfileIdAndAndRateBy(reviewAndRatingDTO.getCandidateId(),reviewAndRatingDTO.getCompanyId(),"candidate");

        if(reviewAndRatingObject.isPresent()){
            return false;
        }

        ReviewAndRating reviewAndRating = new ReviewAndRating();
        reviewAndRating.setRating(reviewAndRatingDTO.getRating());
        reviewAndRating.setReview(reviewAndRatingDTO.getReview());
        reviewAndRating.setDate(new Date());
        reviewAndRating.setCandidateId(reviewAndRatingDTO.getCandidateId());
        reviewAndRating.setRateBy(userType);
        Optional<CompanyProfile> companyProfile = companyProfileRepository.findById(reviewAndRatingDTO.getCompanyId());
        reviewAndRating.setCompanyProfile(companyProfile.get());
        reviewAndRatingRepository.save(reviewAndRating);
        return  true;



    }






    public ApiResponse getAppliedCandidateByJobId(Long jobId){
        Optional<Job> job = jobRepository.findById(jobId);
        Integer count = 0;
        if(job.isPresent()){

           count  = jobRepository.countOfCandidates(jobId);
            count = count!=0?count:0;
            return new ApiResponse(200,"succesfull",count);

        }
        else{
            return new ApiResponse(500,"unsuccessfull",count);

        }
    }

    public ApiResponse getAppliedCandidateProfilesByJobId(Long jobId){
        Optional<Job> job = jobRepository.findById(jobId);
        List<CandidateProfile> candidateProfiles = new ArrayList<>();
        if(job.isPresent()){
            List<Long> idList = jobRepository.findAllCandidateProfile(jobId);
            for (Long candidateId:idList) {
                Optional<CandidateProfile> candidateProfileOptional = candidateProfileRepository.findById(candidateId);

                    if(candidateProfileOptional.isPresent()){
                            CandidateProfile candidateProfile = candidateProfileOptional.get();
                        candidateProfiles.add(candidateProfile);
                    }
            }
            return new ApiResponse(200,"succesfull",candidateProfiles);

            }
        return new ApiResponse(500,"unsuccessfull",null);
    }

    public ApiResponse deleteJobById(Long id,Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userDaoRepository.findByEmail(currentPrincipalName);
        if(user!=null) {

            Boolean jobExist = jobRepository.existsById(id);
            //first delete a job than then its association
            if (jobExist) {
                jobRepository.deleteById(id);
                //now delete its associations in the applied for table
                jobRepository.deleteAssociatedRecords(id);
                return new ApiResponse(200, "Deleted", jobRepository.findJobsByCompanyPaginated(user.getCompanyProfile().getId(),pageable));
            }

        }
        return new ApiResponse(500,"unsuccessfull",null);
    }


    public Page<Job> findByCategory(String cat,int pageNumber){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Pageable pageable = PageRequest.of(pageNumber,5);

        User user = userDaoRepository.findByEmail(currentPrincipalName);

        if(user.getUserType().equalsIgnoreCase("candidate")){
            return jobRepository.findByCategory(cat,pageable);
        }
        else{
            Long companyId = user.getCompanyProfile().getId();
            return jobRepository.findByCategoryAndCompanyId(cat,companyId,pageable);
        }

    }







}

