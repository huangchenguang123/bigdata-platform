import createRequest from "@/service/base";

const smartExecute = createRequest<{ query: string, tables: string[] }, string[][]>(
  '/ad-hot-query/smartExecute',
  {method: 'post'},
);

export default {
  smartExecute
}
