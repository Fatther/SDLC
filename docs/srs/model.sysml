package PriceRadarModel {
    doc /* Модель системы PriceRadar (SysML v2): участники, структура, варианты использования, требования. */

    private import ScalarValues::*;

    // ---------- Перечисления ----------
    enum def Recommendation {
        enum BuyNow;
        enum Wait;
        enum Neutral;
        enum NoData;
    }

    enum def ItemStatus {
        enum Active;
        enum OutOfStock;
        enum CheckError;
        enum Archived;
    }

    enum def Priority {
        enum High;
        enum Medium;
        enum Low;
    }

    // ---------- Данные ----------
    item def Product {
        attribute name : String;
        attribute url : String;
        attribute targetPrice : Real;
        attribute priority : Priority;
        attribute status : ItemStatus;
    }

    item def PricePoint {
        attribute price : Real;
        attribute inStock : Boolean;
        attribute checkedAt : String;
    }

    item def Alert {
        attribute oldPrice : Real;
        attribute newPrice : Real;
        attribute channel : String;
    }

    // ---------- Участники и внешние системы ----------
    part def User;
    part def Administrator :> User;
    part def StoreWebsite;
    part def SmtpServer;
    part def TelegramBotApi;

    // ---------- Структура системы ----------
    part def WebUI;
    part def AccountService;
    part def WishlistService;
    part def PriceMonitor;
    part def AlertService;
    part def PurchaseAdvisor;
    part def DiscountFeed;
    part def BudgetPlanner;
    part def Database;

    part def PriceRadarSystem {
        part webUI : WebUI;
        part accountService : AccountService;
        part wishlistService : WishlistService;
        part priceMonitor : PriceMonitor;
        part alertService : AlertService;
        part purchaseAdvisor : PurchaseAdvisor;
        part discountFeed : DiscountFeed;
        part budgetPlanner : BudgetPlanner;
        part database : Database;
    }

    part priceRadar : PriceRadarSystem;

    // ---------- Варианты использования ----------
    use case def ManageWishlist {
        subject system : PriceRadarSystem;
        actor user : User;
    }

    use case def GetPurchaseAdvice {
        subject system : PriceRadarSystem;
        actor user : User;
        actor store : StoreWebsite;
    }

    use case def ReceiveAlert {
        subject system : PriceRadarSystem;
        actor user : User;
        actor mail : SmtpServer;
        actor bot : TelegramBotApi;
    }

    use case def PlanMonthlyPurchases {
        subject system : PriceRadarSystem;
        actor user : User;
    }

    use case def MaintainSalesCalendar {
        subject system : PriceRadarSystem;
        actor admin : Administrator;
    }

    // ---------- Требования ----------
    requirement def PriceRadarRequirement {
        doc /* Базовое определение требования проекта. */
    }

    requirement <'REQ-FUNC-003'> addByUrl : PriceRadarRequirement {
        doc /* Система должна добавлять товар по ссылке, определяя название, изображение, цену и магазин. */
    }
    requirement <'REQ-FUNC-008'> periodicCheck : PriceRadarRequirement {
        doc /* Система должна проверять цену каждого активного товара с интервалом 6 часов по умолчанию (1-24 часа). */
    }
    requirement <'REQ-FUNC-012'> targetPriceAlert : PriceRadarRequirement {
        doc /* Система должна отправлять алерт, когда цена становится не выше целевой. */
    }
    requirement <'REQ-FUNC-017'> discountFeedReq : PriceRadarRequirement {
        doc /* Система должна показывать ленту скидок, отсортированную по размеру снижения. */
    }
    requirement <'REQ-FUNC-019'> purchaseAdviceReq : PriceRadarRequirement {
        doc /* Система должна вычислять рекомендацию Купить сейчас / Подождать / Нейтрально / Недостаточно данных с обоснованием. */
    }
    requirement <'REQ-FUNC-022'> budgetPlanReq : PriceRadarRequirement {
        doc /* Система должна составлять план покупок, не превышающий месячный бюджет. */
    }
    requirement <'REQ-PERF-004'> advisorSpeed : PriceRadarRequirement {
        doc /* Расчёт рекомендации не более 300 мс; память серверного процесса не более 1 ГБ. */
    }
    requirement <'REQ-SEC-004'> ssrfProtection : PriceRadarRequirement {
        doc /* Система должна отклонять URL с частными адресами и доменами вне списка магазинов. */
    }
    requirement <'REQ-COMP-001'> politeCrawling : PriceRadarRequirement {
        doc /* Не более 1 запроса в 2 секунды на домен, учёт robots.txt. */
    }

    // ---------- Удовлетворение требований ----------
    satisfy addByUrl by priceRadar.wishlistService;
    satisfy periodicCheck by priceRadar.priceMonitor;
    satisfy targetPriceAlert by priceRadar.alertService;
    satisfy discountFeedReq by priceRadar.discountFeed;
    satisfy purchaseAdviceReq by priceRadar.purchaseAdvisor;
    satisfy budgetPlanReq by priceRadar.budgetPlanner;
    satisfy advisorSpeed by priceRadar.purchaseAdvisor;
    satisfy ssrfProtection by priceRadar.wishlistService;
    satisfy politeCrawling by priceRadar.priceMonitor;
}
