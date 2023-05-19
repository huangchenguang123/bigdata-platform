import createRequest from "@/service/base";

const searchTables = createRequest<{}, string[]>(
  '/table/searchTables',
  { method: 'post' },
);

export default {
  searchTables
}
