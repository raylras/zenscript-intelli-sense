package raylras.zen.lsp.provider;

import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SignatureHelpParams;
import org.eclipse.lsp4j.SignatureInformation;
import stanhebben.zenscript.ZenParsedFile;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// TODO: SignatureHelpProvider
public class SignatureHelpProvider {

    private final String uri;
    private final ZenParsedFile parsedFile;

    public SignatureHelpProvider(String uri, ZenParsedFile parsedFile) {
        this.uri = uri;
        this.parsedFile = parsedFile;
    }

    public CompletableFuture<SignatureHelp> provideSignatureHelp(SignatureHelpParams params) {
        List<SignatureInformation> information = new LinkedList<>();

        information.add(new SignatureInformation("label", "doc", new LinkedList<>()));

        SignatureHelp signature = new SignatureHelp(information, 1, 1);

        return CompletableFuture.completedFuture(signature);
    }

}
