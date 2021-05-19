package Controllers.Backend;

public enum RequestType {
    RequestSalt,
    SendSalt,

    SendErrorCode,
    SendSuccessMessage,

    RequestLogin,
    SendLoginToken,

    RequestResetPassword,
    RequestNewUser,

    RequestOrganisation,
    SendOrganisation,

    RequestOrganisationOrders,
    SendOrganisationOrders,

    RequestAllOrders,
    SendAllOrders,

    RequestAddOrder,

    RequestRemoveOrder,

    RequestAssetTypes,
    SendAssetTypes,

    RequestTradeHistory,
    SendTradeHistory,

    RequestAddUser,

    RequestAllUsers,

    RequestUpdateUserPassword,

    RequestUpdateUserAccountType,

    RequestUpdateUserOrganisation,

    RequestAddAsset,

    RequestAddOrganisation,

    RequestAllOrganisations,
    SendAllOrganisations,

    RequestUpdateOrganisationAsset

}
