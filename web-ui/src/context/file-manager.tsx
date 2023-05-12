import React, {createContext, useState} from "react";

export interface IContext {
  model: IModel;
  setModel: (value: IModel) => void;
  setIsUploaderShow: (value: boolean) => void;
}

export interface IModel {
  isUploaderShow: boolean;
}
const initModel: IModel = {
  isUploaderShow: false
}

export const FileManagerContext = createContext<IContext>({} as any);

export default function FileManagerContextProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const setModel = (model: IModel) => {
    setStateModel({
      ...model,
    });
  }

  const [model, setStateModel] = useState<IModel>(initModel);

  const setIsUploaderShow = (
    isUploaderShow: boolean,
  ) => {
    setStateModel({
      ...model,
      isUploaderShow,
    });
  };

  return (
    <FileManagerContext.Provider
      value={{
        model,
        setModel,
        setIsUploaderShow
      }}
    >
      {children}
    </FileManagerContext.Provider>
  );
}
