import { MenuItemConstructorOptions } from 'electron'

export const trayMenus: MenuItemConstructorOptions[] = [
  {
    label: 'Home',
    click: (): void => {
      $tools.createWindow('Home')
    },
  },

  {
    label: 'WorkSpace',
    click: (): void => {
      $tools.createWindow('WorkSpace')
    },
  },

  { type: 'separator' },

  { label: 'Quit', role: 'quit' },
]
