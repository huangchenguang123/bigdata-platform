import React, {createContext, useState} from "react";
import {IFileConsoleBasic} from "@/types";
import {isNil} from "lodash";

export interface IContext {
  model: IModel;
  setModel: (value: IModel) => void;
  setIsUploaderShow: (value: boolean) => void;
  setFileInfo: (value: IFileConsoleBasic) => void;
}

export interface IModel {
  isUploaderShow: boolean;
  fileInfo: IFileConsoleBasic | null;
}
const initModel: IModel = {
  isUploaderShow: false,
  fileInfo: null
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

  const setFileInfo = (
    fileInfo: IFileConsoleBasic,
  ) => {
    setStateModel({
      ...model,
      fileInfo,
    });
  };

  return (
    <FileManagerContext.Provider
      value={{
        model,
        setModel,
        setIsUploaderShow,
        setFileInfo
      }}
    >
      {children}
    </FileManagerContext.Provider>
  );
}
