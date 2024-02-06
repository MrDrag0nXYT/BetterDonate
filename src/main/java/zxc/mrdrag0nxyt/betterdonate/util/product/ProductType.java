package zxc.mrdrag0nxyt.betterdonate.util.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ProductType {
    DONATE("donate", "products.name.donate", "features.donate", "products.donate", "webhooks.donate"),
    MONEY("money", "products.name.money", "features.money", "products.money", "webhooks.money"),
    TOKENS("tokens", "products.name.tokens", "features.tokens", "products.tokens", "webhooks.tokens"),
    COMMAND("command", "products.name.commands", "features.commands", "products.commands", "webhooks.commands");

    private final String name;
    private final String displayNameConfigKey;
    private final String configKey;
    private final String cartKey;
    private final String webhookKey;

    public static ProductType getProductType(String type) {
        return Arrays.stream(ProductType.values())
                .filter(product -> product.name.equalsIgnoreCase(type)
                ).findFirst().get();
    }
}
