import { defineConfig } from 'umi';
const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin');
require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
const UMI_ENV = process.env.UMI_ENV || 'local';

const chainWebpack = (config: any, { webpack }: any) => {
  config.plugin('monaco-editor').use(MonacoWebpackPlugin, [
    {
      languages: ['mysql', 'pgsql', 'sql'],
    },
  ]);
};

export default defineConfig({
  title: 'Chat2DB',
  history: {
    type: 'hash',
  },
  base: '/',
  publicPath: '/',
  hash: false,
  routes: [
    {
      path: '/',
      component: '@/components/AppContainer',
      routes: [
        {
          path: '/',
          component: '@/layouts/BaseLayout',
          routes: [
            {
              exact: true,
              path: '/',
              component: '@/pages/database',
            },
            {
              exact: true,
              path: '/database',
              component: '@/pages/database',
            },
            {
              path: '/file-manager',
              exact: true,
              component: '@/pages/file-manager'
            }
          ]
        }
      ],
    },
  ],
  mfsu: {},
  fastRefresh: {},
  dynamicImport: {
    loading: '@/components/Loading/LazyLoading'
  },
  nodeModulesTransform: {
    type: 'none',
  },
  chainWebpack,
  devServer: {
    port: 8001,
    host: '127.0.0.1',
  },
  define: {
    'process.env.UMI_ENV': UMI_ENV,
  }
});
