import createRequest from "@/service/base";
import {IResult} from "@/types";

const searchTables = createRequest<{}, IResult<string[]>>(
  '/table/searchTables',
  { method: 'post' },
);

export default {
  searchTables
}
