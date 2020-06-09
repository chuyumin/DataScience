package recommend;


import java.io.IOException;
import java.util.*;

public class UserBasedCF {
    private static String UserContentJson = FileUtil.readJsonFile("D:\\ChromeCoreDownloads\\sample.json");
    private static List<User> userList = JsonUtil.DealJson(UserContentJson);
    private static Map<String , String> caseidToaddress = JsonUtil.casetable(UserContentJson);
    private static Map<String , Integer> useridToindex= new HashMap<>();
    private static Map<String , Set<String>> case_user_Collection = new HashMap<>();

    public List<String> recommend_case(String user_id) {
            Similarity similarity = calculateSimilarity();
            int user_index = useridToindex.get(user_id);
            double[][] matrix_y = similarity.getMatrix_y();
            double[][] matrix_x1 = similarity.getMatrix_x1();
            double[][] matrix_x2 = similarity.getMatrix_x2();
            double max_similarity = 0.0;
            int recommenduser_index = -1;
            for (int i = 0 ; i < matrix_y[0].length ; i ++) {
                if (i == user_index)
                    continue;
                if (matrix_x1[user_index][i] == 0 || matrix_x2[user_index][i] == 0 )
                    continue;
                double temp_similarity = matrix_y[user_index][i] /(Math.sqrt(matrix_x1[user_index][i]) *
                        Math.sqrt(matrix_x2[user_index][i]));
                if (temp_similarity >= max_similarity) {
                    recommenduser_index = i;
                    max_similarity = temp_similarity;
                }
            }
            System.out.println("最大相似度:" + max_similarity);
            User recommend_user = userList.get(recommenduser_index);
            System.out.println(recommend_user);
            List<String> recommend_cases = new ArrayList<>(recommend_user.getCases().keySet());
            Iterator<String> iterator = recommend_cases.iterator();
            while (iterator.hasNext()) {
                String case_id = iterator.next();
                for (String done_case : userList.get(user_index).getCases().keySet())
                    if (done_case.equals(case_id))
                        iterator.remove();
            }
            List<String> recommend_case_addresses = new ArrayList<>();
            recommend_cases.forEach((recommend_case) -> recommend_case_addresses.add(caseidToaddress.get(recommend_case)));
            System.out.println(recommend_case_addresses.size());
            return recommend_case_addresses;
    }

    public Similarity calculateSimilarity() {
        init_useridToindex();
        init_case_user_Collection();
        int user_length = userList.size();
        System.out.println(userList.get(0).getUser_id());
        System.out.println(userList.get(0).getCases().keySet().size());
        System.out.println(userList.get(9).getCases().keySet().size());
        double[][] matrix_y = new double[user_length][user_length];
        double[][] matrix_x1 = new double[user_length][user_length];
        double[][] matrix_x2 = new double[user_length][user_length];
        Set<Map.Entry<String, Set<String>>> entrySet = case_user_Collection.entrySet();
        Iterator<Map.Entry<String, Set<String>>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Set<String>> current = iterator.next();
            String case_id = current.getKey();
            Set<String> commonUsers = current.getValue();
            for (String user_u : commonUsers) {
                for (String user_v : commonUsers) {
                    if(user_u.equals(user_v)){
                        continue;
                    }
                    int user_index1 = useridToindex.get(user_u);
                    int user_index2 = useridToindex.get(user_v);
                    matrix_y[user_index1][user_index2] +=
                            userList.get(user_index1).getCases().get(case_id) * 1.0/100 *
                                    userList.get(user_index2).getCases().get(case_id) * 1.0/100;
                    matrix_x1[user_index1][user_index2] +=
                            userList.get(user_index1).getCases().get(case_id) * 1.0/100 *
                                    userList.get(user_index1).getCases().get(case_id) * 1.0/100;
                    matrix_x2[user_index1][user_index2] +=
                            userList.get(user_index2).getCases().get(case_id) * 1.0/100 *
                                    userList.get(user_index2).getCases().get(case_id) * 1.0/100;
                }
            }
        }

        System.out.println(matrix_y[0][9]);
        System.out.println(matrix_x1[0][9]);
        System.out.println(matrix_x2[0][9]);
        return new Similarity(matrix_y , matrix_x1 , matrix_x2);
    }

    public void init_useridToindex() {
        int user_length = userList.size();
        for (int i = 0 ; i < user_length ; i ++)
            useridToindex.put(userList.get(i).getUser_id() , i);
    }

    public void init_case_user_Collection() {
        //记录该题目是否已经加入map中
        Set<String> cases = new HashSet<>();
        for (User user : userList) {
            for (String case_id : user.getCases().keySet()) {
                if (cases.contains(case_id)) {
                    case_user_Collection.get(case_id).add(user.getUser_id());
                }
                else {
                    cases.add(case_id);
                    case_user_Collection.put(case_id , new HashSet<String>());
                    case_user_Collection.get(case_id).add(user.getUser_id());
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        UserBasedCF cf = new UserBasedCF();
//        cf.calculateSimilarity();
        System.out.println(cf.recommend_case("3544"));
    }
}
