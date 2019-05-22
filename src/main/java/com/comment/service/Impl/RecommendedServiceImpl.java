package com.comment.service.Impl;

import java.util.ArrayList;
import java.util.List;

import com.comment.service.RecommendedService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.librec.conf.Configuration;
import net.librec.conf.Configuration.Resource;
import net.librec.data.DataModel;
import net.librec.data.model.TextDataModel;
import net.librec.eval.RecommenderEvaluator;
import net.librec.eval.rating.MAEEvaluator;
import net.librec.eval.rating.RMSEEvaluator;
import net.librec.filter.GenericRecommendedFilter;
import net.librec.filter.RecommendedFilter;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.cf.ItemKNNRecommender;
import net.librec.recommender.cf.UserKNNRecommender;
import net.librec.recommender.item.RecommendedItem;
import net.librec.similarity.PCCSimilarity;
import net.librec.similarity.RecommenderSimilarity;

@Service
public class RecommendedServiceImpl implements RecommendedService {
    @Value("${file.save.path}")
    private String filePath;

    /**
     * 从文件查询推荐列表
     * 根据公式计算相似度
     * @param userId 用户id
     * @param itemId 商品id
     * @return
     * @throws Exception
     */
    public List<RecommendedItem> getItemListFromText(String userId, String itemId) throws Exception {
        //在java实现中,实例Configuration对象, DataModel对象, Similarity矩阵对象之后,作为RecommenderContext的构造器参数生成RecommenderContext的对象.
        // build data model
        Configuration conf = new Configuration();
        System.out.println("配置文件的内容："+conf.toString());
        conf.set("dfs.data.dir", filePath);   //通过该路径读取数据集
        conf.set("data.input.path", "/recommend/ratings.txt");   //存放推荐列表
        TextDataModel dataModel = new TextDataModel(conf);
        dataModel.buildDataModel();

        // build recommender context
        RecommenderContext context = new RecommenderContext(conf, dataModel);

        // build similarity
        conf.set("rec.recommender.similarity.key", "item");
        RecommenderSimilarity similarity = new PCCSimilarity();  //pcc距离度量算法
        similarity.buildSimilarityMatrix(dataModel);   //调用buildSimilarityMatrix方法来进行相似度矩阵的计算
        context.setSimilarity(similarity);     //构建userId与itemId之间的距离

        // build recommender
        conf.set("rec.neighbors.knn.number", "5");  //最多推荐5条数据
        Recommender recommender = new ItemKNNRecommender();  //采用的K临近推荐算法
        recommender.setContext(context);

        // run recommender algorithm
        recommender.recommend(context);

        // evaluate the recommended result
        RecommenderEvaluator evaluator = new RMSEEvaluator();
        System.out.println("RMSE:" + recommender.evaluate(evaluator));

        // filter the recommended result
        List<RecommendedItem> recommendedItemList = recommender.getRecommendedList(); //RecommendedItem中的Item就是bussinessitem
        GenericRecommendedFilter filter = new GenericRecommendedFilter();

        if (userId != null) {
            List<String> userIdList = new ArrayList<>();
            userIdList.add(userId);
            filter.setUserIdList(userIdList);
        }
        if (itemId != null) {
            List<String> itemIdList = new ArrayList<>();
            itemIdList.add(itemId);
            filter.setItemIdList(itemIdList);
        }
        recommendedItemList = filter.filter(recommendedItemList);

        return recommendedItemList;  //返回推荐列表
    }


}

