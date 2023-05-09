const routes: RouteConfig[] = [
  {
    name: 'Home',
    path: '/',
    redirectTo: '/workspace?form=home',
    windowOptions: {
      title: 'App Home (redirect to workspace)',
      width: 1200,
      height: 800,
      minWidth: 800,
      minHeight: 600,
    },
    createConfig: {
      showSidebar: true,
      showCustomTitlebar: true,
      saveWindowBounds: true,
      openDevTools: true,
    },
  },
]

export default routes
