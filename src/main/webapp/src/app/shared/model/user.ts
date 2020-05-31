import { ICamera } from './camera';

export interface IUser {
    name: string;
    email: string;
    picture: string;
    maxEnergyConsumption: number;
    cams: ICamera[];
}
