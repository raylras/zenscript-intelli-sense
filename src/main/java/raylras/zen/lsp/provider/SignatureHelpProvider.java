package raylras.zen.lsp.provider;

import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SignatureHelpParams;

import java.util.Collections;

// TODO: SignatureHelpProvider
public class SignatureHelpProvider {

    public SignatureHelp provideSignatureHelp(SignatureHelpParams params) {
        return empty();
    }

    public static SignatureHelp empty() {
        return new SignatureHelp(Collections.emptyList(), 0, 0);
    }

}
