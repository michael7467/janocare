export interface ProfessionalSidebar {
  tittle: string;
  showAsTab: boolean;
  separateRoute: boolean;
  menu: ProfessionalMenu[];
}
export interface ProfessionalMenu {
  menuValue: string;
  hasSubRoute: boolean;
  showSubRoute: boolean;
  route?: string;
  icon: string;
  subMenus?: ProfessionalSubMenus[];
  base ?: string;
}
export interface ProfessionalSubMenus {
  menuValue?: string;
  route?: string;
}
export interface MenuItem {
  id?: number;
  label?: any;
  icon?: string;
  link?: string;
  subItems?: any;
  isTitle?: boolean;
  badge?: any;
  parentId?: number;
  isLayout?: boolean;
  gname?: string[];
}

export interface SubMenu {
  menuValue?: string;
  route?: string;
  hasSubRoute?: boolean;
  showSubRoute?: boolean;
  openInNewTab?: boolean;
  subMenus: SubMenu[];
}

export interface Menu {
  menuValue: string;
  img?: string;
  route?: string;
  hasSubRoute: boolean;
  showSubRoute: boolean;
  openInNewTab?: boolean;
  subMenus: SubMenu[];
}

export interface header {
  tittle: string;
  showAsTab: boolean;
  separateRoute: boolean;
  // menu: Menu[];
  id:string;
}