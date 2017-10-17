package Evaluation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Created by Lee on 2017/5/23 0023.
 */
@SpringBootApplication
public class App {

//    @Autowired
//    private static CategoryDao categoryDao;

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);

        //initData();
    }

//    private static void initData() {
//        Category hotCategory=new Category();
//        hotCategory.setName("hot");
//        hotCategory.setFlag(true);
//
//        for (int i=0;i<20;i++){
//            News hotNews=new News();
//            hotNews.setTitle("hotTitle"+i);
//            hotNews.setContent("hotContent"+i);
//            hotNews.setDate(new Date());
//            hotNews.setNewsManagerName("admin"+i);
//
//            hotCategory.getNewsList().add(hotNews);
//            hotNews.setCategory(hotCategory);
//
//        }
//
//        //categoryDao.save(hotCategory);
//    }
}
