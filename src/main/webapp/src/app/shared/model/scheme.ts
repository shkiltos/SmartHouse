export interface Scheme {
    id: string;
    title: string;
    image: any;
    dashboardSchemeItems: SchemeItem[];
}

export interface SchemeItem {
    deviceId: string;
    x: number;
    y: number;
}
