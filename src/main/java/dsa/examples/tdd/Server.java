
    package dsa.example.server;  // 替换为你实际的包名

import static spark.Spark.*;

    public class Server {
        public static void main(String[] args) {
            port(4567); // 设置端口号为 4567
            get("/repos", (request, response) -> {
                String username = request.queryParams("username");
                if (username == null || username.isEmpty()) {
                    return "Username is required";
                }
                try {
                    // 调用你的 GitHub 请求逻辑
                    List<Rep> repos = Gith.fetchReposFromGitHub(username);
                    return reposToJson(repos);  // 将结果转换为 JSON 并返回
                } catch (Exception e) {
                    response.status(500);
                    return "Failed to fetch repos: " + e.getMessage();
                }
            });
        }

        // 将 Repo 列表转换为 JSON 格式
        private static String reposToJson(List<Rep> repos) {
            JSONArray jsonArray = new JSONArray();
            for (Rep repo : repos) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", repo.getName());
                jsonObject.put("description", repo.getDescription());
                jsonObject.put("stars", repo.getStargazersCount());
                jsonArray.put(jsonObject);
            }
            return jsonArray.toString();
        }
    }

