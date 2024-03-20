# File
This document delineates the protocol for communication with the **Client Robomap Cloud** desktop application using the **file system**. Essentially, it outlines the method of exchanging information by generating files in a specific format and subsequently transmitting these files to the platform automatically.

## File Format
Various types of data can be transmitted from the local machine to the Client Robomap Cloud. These types are as follows:
- **Data Incidences.** This encompasses all data related to system incidents, providing crucial information about the system's operation status.
- **Intralogistics Containers.** Data pertaining to intralogistics containers.
- **Data Frames.** This category encompasses any type of data relevant to the system's operation, including production data, operational data, device data, or any other relevant information.
- **Data System.** This category covers data related to the Operating System (OS), such as network downtime, CPU usage, memory usage, and other system-related metrics.

### Filename
The filename structure for these files follows a specific convention based on the type of data being transmitted.
`ID_<PLC_ID>_<DATE dd.mm.yy.hh.mm.ss>.txt`

### File content
Each file will contain one of the following contents based on the type of data:

- **Data Incidence.** This section will contain information related to system incidents.
    `DATA_INCIDENCE_<ID>_<STATUS:1/2>_<SEVERITY:WARNING,ERROR,INFO>_<SEVERITY:WARNING,ERROR,INFO>_<AREA/MAQUINA>_<SUBMAQUINA>_<NAME>_<12-02-2024:23:09:22>;`
- **Data Intralogistics Containers.** Containers: Data concerning intralogistics containers.
    `DATA_INTRALOGISTICS_<BARCODE>_<CHUTE>_<STATUS>_<12-02-2024:12:23:22>;`
- **Data Frames.** This section will include various types of relevant operational data.
    `DATA_FRAME_<ID>_<MAQUINA>_<SUBMAQUINA>_Porcentaje de llenado_30.0_<02-02-2024:12:23:22>;`
- **Data System.** This part will encompass system-related metrics and data.
    `DATA_SYSTEM_<>.`

## Example
- [Click here](https://github.com/robomap/Client-Robomap-Cloud/blob/main/documentation/Files) to see an **example** of the files needed to exchange information through the file protocol. This example contains.
