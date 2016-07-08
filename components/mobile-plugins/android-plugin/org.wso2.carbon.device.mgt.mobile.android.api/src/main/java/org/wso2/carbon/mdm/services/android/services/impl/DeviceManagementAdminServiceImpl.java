/*
 *   Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */
package org.wso2.carbon.mdm.services.android.services.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.device.mgt.common.DeviceManagementException;
import org.wso2.carbon.device.mgt.common.operation.mgt.Operation;
import org.wso2.carbon.device.mgt.common.operation.mgt.OperationManagementException;
import org.wso2.carbon.device.mgt.core.operation.mgt.CommandOperation;
import org.wso2.carbon.device.mgt.core.operation.mgt.ProfileOperation;
import org.wso2.carbon.mdm.services.android.bean.*;
import org.wso2.carbon.mdm.services.android.bean.wrapper.*;
import org.wso2.carbon.mdm.services.android.exception.BadRequestException;
import org.wso2.carbon.mdm.services.android.exception.UnexpectedServerErrorException;
import org.wso2.carbon.mdm.services.android.services.DeviceManagementAdminService;
import org.wso2.carbon.mdm.services.android.util.AndroidAPIUtils;
import org.wso2.carbon.mdm.services.android.util.AndroidConstants;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/admin/devices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeviceManagementAdminServiceImpl implements DeviceManagementAdminService {

    private static final Log log = LogFactory.getLog(DeviceManagementAdminServiceImpl.class);

    @POST
    @Path("/lock-devices")
    @Override
    public Response configureDeviceLock(DeviceLockBeanWrapper deviceLockBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking Android device lock operation");
        }

        try {
            if (deviceLockBeanWrapper == null || deviceLockBeanWrapper.getOperation() == null) {
                String errorMessage = "Lock bean is empty.";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            DeviceLock lock = deviceLockBeanWrapper.getOperation();
            ProfileOperation operation = new ProfileOperation();
            operation.setCode(AndroidConstants.OperationCodes.DEVICE_LOCK);
            operation.setType(Operation.Type.PROFILE);
            operation.setEnabled(true);
            operation.setPayLoad(lock.toJSON());
            return AndroidAPIUtils.getOperationResponse(deviceLockBeanWrapper.getDeviceIDs(), operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/unlock-devices")
    @Override
    public Response configureDeviceUnlock(List<String> deviceIDs) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking Android device unlock operation.");
        }

        try {
            CommandOperation operation = new CommandOperation();
            operation.setCode(AndroidConstants.OperationCodes.DEVICE_UNLOCK);
            operation.setType(Operation.Type.COMMAND);
            operation.setEnabled(true);
            return AndroidAPIUtils.getOperationResponse(deviceIDs, operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/location")
    @Override
    public Response getDeviceLocation(List<String> deviceIDs) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking Android device location operation.");
        }

        try {
            CommandOperation operation = new CommandOperation();
            operation.setCode(AndroidConstants.OperationCodes.DEVICE_LOCATION);
            operation.setType(Operation.Type.COMMAND);
            return AndroidAPIUtils.getOperationResponse(deviceIDs, operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/clear-password")
    @Override
    public Response removePassword(List<String> deviceIDs) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking Android clear password operation.");
        }

        try {
            CommandOperation operation = new CommandOperation();
            operation.setCode(AndroidConstants.OperationCodes.CLEAR_PASSWORD);
            operation.setType(Operation.Type.COMMAND);
            return AndroidAPIUtils.getOperationResponse(deviceIDs, operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance.";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/control-camera")
    @Override
    public Response configureCamera(CameraBeanWrapper cameraBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking Android Camera operation");
        }

        try {
            if (cameraBeanWrapper == null || cameraBeanWrapper.getOperation() == null) {
                String errorMessage = "The payload of the configure camera operation is incorrect.";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            Camera camera = cameraBeanWrapper.getOperation();
            CommandOperation operation = new CommandOperation();
            operation.setCode(AndroidConstants.OperationCodes.CAMERA);
            operation.setType(Operation.Type.COMMAND);
            operation.setEnabled(camera.isEnabled());
            return AndroidAPIUtils.getOperationResponse(cameraBeanWrapper.getDeviceIDs(), operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/info")
    @Override
    public Response getDeviceInformation(List<String> deviceIDs) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking get Android device information operation");
        }

        try {
            CommandOperation operation = new CommandOperation();
            operation.setCode(AndroidConstants.OperationCodes.DEVICE_INFO);
            operation.setType(Operation.Type.COMMAND);
            return AndroidAPIUtils.getOperationResponse(deviceIDs, operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/enterprise-wipe")
    @Override
    public Response wipeDevice(List<String> deviceIDs) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking enterprise-wipe device operation");
        }

        try {
            CommandOperation operation = new CommandOperation();
            operation.setCode(AndroidConstants.OperationCodes.ENTERPRISE_WIPE);
            operation.setType(Operation.Type.COMMAND);
            return AndroidAPIUtils.getOperationResponse(deviceIDs, operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/wipe")
    @Override
    public Response wipeData(WipeDataBeanWrapper wipeDataBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking Android wipe-data device operation");
        }

        try {
            if (wipeDataBeanWrapper == null || wipeDataBeanWrapper.getOperation() == null) {
                String errorMessage = "WipeData bean is empty.";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            WipeData wipeData = wipeDataBeanWrapper.getOperation();
            ProfileOperation operation = new ProfileOperation();
            operation.setCode(AndroidConstants.OperationCodes.WIPE_DATA);
            operation.setType(Operation.Type.PROFILE);
            operation.setPayLoad(wipeData.toJSON());
            return AndroidAPIUtils.getOperationResponse(wipeDataBeanWrapper.getDeviceIDs(), operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/applications")
    @Override
    public Response getApplications(List<String> deviceIDs) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking Android getApplicationList device operation");
        }

        try {
            CommandOperation operation = new CommandOperation();
            operation.setCode(AndroidConstants.OperationCodes.APPLICATION_LIST);
            operation.setType(Operation.Type.COMMAND);
            return AndroidAPIUtils.getOperationResponse(deviceIDs, operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/ring")
    @Override
    public Response ringDevice(List<String> deviceIDs) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking Android ring-device device operation");
        }

        try {
            CommandOperation operation = new CommandOperation();
            operation.setCode(AndroidConstants.OperationCodes.DEVICE_RING);
            operation.setType(Operation.Type.COMMAND);
            return AndroidAPIUtils.getOperationResponse(deviceIDs, operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/reboot")
    @Override
    public Response rebootDevice(List<String> deviceIDs) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking Android reboot-device device operation");
        }

        try {
            CommandOperation operation = new CommandOperation();
            operation.setCode(AndroidConstants.OperationCodes.DEVICE_REBOOT);
            operation.setType(Operation.Type.COMMAND);
            return AndroidAPIUtils.getOperationResponse(deviceIDs, operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/mute")
    @Override
    public Response muteDevice(List<String> deviceIDs) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking mute device operation");
        }

        try {
            CommandOperation operation = new CommandOperation();
            operation.setCode(AndroidConstants.OperationCodes.DEVICE_MUTE);
            operation.setType(Operation.Type.COMMAND);
            operation.setEnabled(true);
            return AndroidAPIUtils.getOperationResponse(deviceIDs, operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/install-application")
    @Override
    public Response installApplication(ApplicationInstallationBeanWrapper applicationInstallationBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking 'InstallApplication' operation");
        }

        try {
            if (applicationInstallationBeanWrapper == null || applicationInstallationBeanWrapper.getOperation() ==
                    null) {
                String errorMessage = "The payload of the application installing operation is incorrect";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            ApplicationInstallation applicationInstallation = applicationInstallationBeanWrapper.getOperation();
            ProfileOperation operation = new ProfileOperation();
            operation.setCode(AndroidConstants.OperationCodes.INSTALL_APPLICATION);
            operation.setType(Operation.Type.PROFILE);
            operation.setPayLoad(applicationInstallation.toJSON());
            return AndroidAPIUtils.getOperationResponse(applicationInstallationBeanWrapper.getDeviceIDs(),
                    operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/update-application")
    @Override
    public Response updateApplication(ApplicationUpdateBeanWrapper applicationUpdateBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking 'UpdateApplication' operation");
        }

        try {
            if (applicationUpdateBeanWrapper == null || applicationUpdateBeanWrapper.getOperation() == null) {
                String errorMessage = "The payload of the application update operation is incorrect";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            ApplicationUpdate applicationUpdate = applicationUpdateBeanWrapper.getOperation();
            ProfileOperation operation = new ProfileOperation();
            operation.setCode(AndroidConstants.OperationCodes.UPDATE_APPLICATION);
            operation.setType(Operation.Type.PROFILE);
            operation.setPayLoad(applicationUpdate.toJSON());

            return AndroidAPIUtils.getOperationResponse(applicationUpdateBeanWrapper.getDeviceIDs(),
                    operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/uninstall-application")
    @Override
    public Response uninstallApplication(ApplicationUninstallationBeanWrapper applicationUninstallationBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking 'UninstallApplication' operation");
        }

        try {
            if (applicationUninstallationBeanWrapper == null ||
                    applicationUninstallationBeanWrapper.getOperation() == null) {
                String errorMessage = "The payload of the application uninstalling operation is incorrect";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            ApplicationUninstallation applicationUninstallation = applicationUninstallationBeanWrapper.getOperation();
            ProfileOperation operation = new ProfileOperation();
            operation.setCode(AndroidConstants.OperationCodes.UNINSTALL_APPLICATION);
            operation.setType(Operation.Type.PROFILE);
            operation.setPayLoad(applicationUninstallation.toJSON());

            return AndroidAPIUtils.getOperationResponse(applicationUninstallationBeanWrapper.getDeviceIDs(),
                    operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/blacklist-applications")
    @Override
    public Response blacklistApplications(BlacklistApplicationsBeanWrapper blacklistApplicationsBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking 'Blacklist-Applications' operation");
        }

        try {
            if (blacklistApplicationsBeanWrapper == null || blacklistApplicationsBeanWrapper.getOperation() == null) {
                String errorMessage = "The payload of the blacklisting apps operation is incorrect";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            BlacklistApplications blacklistApplications = blacklistApplicationsBeanWrapper.getOperation();
            ProfileOperation operation = new ProfileOperation();
            operation.setCode(AndroidConstants.OperationCodes.BLACKLIST_APPLICATIONS);
            operation.setType(Operation.Type.PROFILE);
            operation.setPayLoad(blacklistApplications.toJSON());
            return AndroidAPIUtils.getOperationResponse(blacklistApplicationsBeanWrapper.getDeviceIDs(),
                    operation);

        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/upgrade-firmware")
    @Override
    public Response upgradeFirmware(UpgradeFirmwareBeanWrapper upgradeFirmwareBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking Android upgrade-firmware device operation");
        }

        try {
            if (upgradeFirmwareBeanWrapper == null || upgradeFirmwareBeanWrapper.getOperation() == null) {
                String errorMessage = "The payload of the upgrade firmware operation is incorrect";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            UpgradeFirmware upgradeFirmware = upgradeFirmwareBeanWrapper.getOperation();
            ProfileOperation operation = new ProfileOperation();
            operation.setCode(AndroidConstants.OperationCodes.UPGRADE_FIRMWARE);
            operation.setType(Operation.Type.PROFILE);
            operation.setPayLoad(upgradeFirmware.toJSON());
            return AndroidAPIUtils.getOperationResponse(upgradeFirmwareBeanWrapper.getDeviceIDs(), operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/configure-vpn")
    @Override
    public Response configureVPN(@Valid VpnBeanWrapper vpnBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking Android VPN device operation");
        }

        try {
            if (vpnBeanWrapper == null || vpnBeanWrapper.getOperation() == null) {
                String errorMessage = "The payload of the VPN operation is incorrect";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            Vpn vpn = vpnBeanWrapper.getOperation();
            ProfileOperation operation = new ProfileOperation();
            operation.setCode(AndroidConstants.OperationCodes.VPN);
            operation.setType(Operation.Type.PROFILE);
            operation.setPayLoad(vpn.toJSON());
            return AndroidAPIUtils.getOperationResponse(vpnBeanWrapper.getDeviceIDs(),
                    operation);
        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/send-notification")
    @Override
    public Response sendNotification(NotificationBeanWrapper notificationBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking 'notification' operation");
        }

        try {
            if (notificationBeanWrapper == null || notificationBeanWrapper.getOperation() == null) {
                String errorMessage = "The payload of the notification operation is incorrect";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            Notification notification = notificationBeanWrapper.getOperation();
            ProfileOperation operation = new ProfileOperation();
            operation.setCode(AndroidConstants.OperationCodes.NOTIFICATION);
            operation.setType(Operation.Type.PROFILE);
            operation.setPayLoad(notification.toJSON());
            return AndroidAPIUtils.getOperationResponse(notificationBeanWrapper.getDeviceIDs(),
                    operation);

        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/configure-wifi")
    @Override
    public Response configureWifi(WifiBeanWrapper wifiBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking 'configure wifi' operation");
        }

        try {
            if (wifiBeanWrapper == null || wifiBeanWrapper.getOperation() == null) {
                String errorMessage = "The payload of the wifi operation is incorrect";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            Wifi wifi = wifiBeanWrapper.getOperation();
            ProfileOperation operation = new ProfileOperation();
            operation.setCode(AndroidConstants.OperationCodes.WIFI);
            operation.setType(Operation.Type.PROFILE);
            operation.setPayLoad(wifi.toJSON());

            return AndroidAPIUtils.getOperationResponse(wifiBeanWrapper.getDeviceIDs(),
                    operation);

        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/encrypt-storage")
    @Override
    public Response encryptStorage(EncryptionBeanWrapper encryptionBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking 'encrypt' operation");
        }

        try {
            if (encryptionBeanWrapper == null || encryptionBeanWrapper.getOperation() == null) {
                String errorMessage = "The payload of the device encryption operation is incorrect";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            DeviceEncryption deviceEncryption = encryptionBeanWrapper.getOperation();
            CommandOperation operation = new CommandOperation();
            operation.setCode(AndroidConstants.OperationCodes.ENCRYPT_STORAGE);
            operation.setType(Operation.Type.COMMAND);
            operation.setEnabled(deviceEncryption.isEncrypted());
            return AndroidAPIUtils.getOperationResponse(encryptionBeanWrapper.getDeviceIDs(),
                    operation);

        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/change-lock-code")
    @Override
    public Response changeLockCode(LockCodeBeanWrapper lockCodeBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking 'change lock code' operation");
        }

        try {
            if (lockCodeBeanWrapper == null || lockCodeBeanWrapper.getOperation() == null) {
                String errorMessage = "The payload of the change lock code operation is incorrect";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            LockCode lockCode = lockCodeBeanWrapper.getOperation();
            ProfileOperation operation = new ProfileOperation();
            operation.setCode(AndroidConstants.OperationCodes.CHANGE_LOCK_CODE);
            operation.setType(Operation.Type.PROFILE);
            operation.setPayLoad(lockCode.toJSON());
            return AndroidAPIUtils.getOperationResponse(lockCodeBeanWrapper.getDeviceIDs(),
                    operation);

        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("/set-password-policy")
    @Override
    public Response setPasswordPolicy(PasswordPolicyBeanWrapper passwordPolicyBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking 'password policy' operation");
        }

        try {
            if (passwordPolicyBeanWrapper == null || passwordPolicyBeanWrapper.getOperation() == null) {
                String errorMessage = "The payload of the change password policy operation is incorrect";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            PasscodePolicy passcodePolicy = passwordPolicyBeanWrapper.getOperation();
            ProfileOperation operation = new ProfileOperation();
            operation.setCode(AndroidConstants.OperationCodes.PASSCODE_POLICY);
            operation.setType(Operation.Type.PROFILE);
            operation.setPayLoad(passcodePolicy.toJSON());

            return AndroidAPIUtils.getOperationResponse(passwordPolicyBeanWrapper.getDeviceIDs(),
                    operation);

        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

    @POST
    @Path("set-webclip")
    @Override
    public Response setWebClip(WebClipBeanWrapper webClipBeanWrapper) {
        if (log.isDebugEnabled()) {
            log.debug("Invoking 'webclip' operation");
        }

        try {

            if (webClipBeanWrapper == null || webClipBeanWrapper.getOperation() == null) {
                String errorMessage = "The payload of the add webclip operation is incorrect";
                log.error(errorMessage);
                throw new BadRequestException(
                        new ErrorResponse.ErrorResponseBuilder().setCode(400l).setMessage(errorMessage).build());
            }
            WebClip webClip = webClipBeanWrapper.getOperation();
            ProfileOperation operation = new ProfileOperation();
            operation.setCode(AndroidConstants.OperationCodes.WEBCLIP);
            operation.setType(Operation.Type.PROFILE);
            operation.setPayLoad(webClip.toJSON());
            return AndroidAPIUtils.getOperationResponse(webClipBeanWrapper.getDeviceIDs(), operation);

        } catch (OperationManagementException e) {
            String errorMessage = "Issue in retrieving operation management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        } catch (DeviceManagementException e) {
            String errorMessage = "Issue in retrieving device management service instance";
            log.error(errorMessage, e);
            throw new UnexpectedServerErrorException(
                    new ErrorResponse.ErrorResponseBuilder().setCode(500l).setMessage(errorMessage).build());
        }
    }

}