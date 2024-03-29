package Controllers.BackEnd;

/**
 * Types of requests
 */
public enum RequestType {
    RequestSalt,
    SendSalt,

    SendErrorCode,
    SendAuthenticationError,
    SendSuccessMessage,

    RequestLogin,
    SendLoginToken,

    RequestResetPassword,

    RequestUserInfo,
    SendUserInfo,

    RequestBuyOrders,
    SendOrders,

    RequestOrganisation,
    SendOrganisation,

    RequestOrganisationOrders,

    RequestAllOrders,

    RequestAddOrder,

    RequestRemoveOrder,

    RequestAssetTypes,
    SendAssetTypes,

    RequestTradeHistory,
    RequestAllTradeHistory,
    SendTradeHistory,

    RequestAddUser,

    RequestAllUsers,
    SendAllUsers,

    RequestUpdateUserPassword,

    RequestUpdateUserAccountType,

    RequestUpdateUserOrganisation,

    RequestAddAsset,

    RequestAddOrganisation,

    RequestAllOrganisations,
    SendAllOrganisations,

    RequestSellOrders,
    RequestUpdateOrganisationAsset,

    RequestUpdateOrganisationCredit

}
