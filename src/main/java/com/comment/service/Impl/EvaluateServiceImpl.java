package com.comment.service.Impl;

import com.comment.domain.Business;
import com.comment.domain.Evaluate;
import com.comment.repository.BusinessJpaRepository;
import com.comment.repository.EvaluateJpaRepository;
import com.comment.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class EvaluateServiceImpl implements EvaluateService {
    @Autowired
    private EvaluateJpaRepository evaluateJpaRepository;

    @Autowired
    private BusinessJpaRepository businessJpaRepository;

    @Override
    public void Convergence(Evaluate evaluate) {
        List<Evaluate> evaluates = evaluateJpaRepository.findAllByBussinessid(evaluate.getBussinessid());
        double s1 = evaluates.stream().filter(r -> r.getStatus() == 1).mapToDouble(Evaluate::getEnv).sum();
        Long c1 = evaluates.stream().filter(r -> r.getStatus() == 1).count();
        double s2 = evaluates.stream().filter(r -> r.getStatus() == 1).mapToDouble(Evaluate::getService).sum();
        double s3 = evaluates.stream().filter(r -> r.getStatus() == 1).mapToDouble(Evaluate::getFlavor).sum();
        double s4 = evaluates.stream().filter(r -> r.getStatus() == 1).mapToDouble(Evaluate::getOverall).sum();

        List<Business> businesses = businessJpaRepository.findAllById(evaluate.getBussinessid());
        if (businesses.size() > 0) {
            Business bsbs = businesses.get(0);
            if (c1 != 0) {
                DecimalFormat df = new DecimalFormat("0.00");
                bsbs.setEnv(Float.parseFloat(df.format(s1 / c1)));
                bsbs.setService(Float.parseFloat(df.format(s2 / c1)));
                bsbs.setFlavor(Float.parseFloat(df.format(s3 / c1)));
                bsbs.setSum(Float.parseFloat(df.format(s4/c1)));
            }
            businessJpaRepository.save(bsbs);
        }
    }
}
