如果需要返回实体，可以在这里定义实体<br/>
请求示例：实体class result = PubgApi.useApi(PubgApiEnum.PUBG_API_MATCHES_ID).call(id,实体class);
<br/>例如：JSONObject result = PubgApi.useApi(PubgApiEnum.PUBG_API_MATCHES_ID).call(id,JSONObject.class);