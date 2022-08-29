package jonatan.andrei.service;

import jonatan.andrei.dto.SettingsDto;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SettingsService {

    @ConfigProperty(name = "integration.settings.default_length_questions_list_page")
    Integer defaultLengthQuestionListPage;

    public SettingsDto getSettings() {
        return SettingsDto.builder()
                .defaultLengthQuestionListPage(defaultLengthQuestionListPage)
                .build();
    }
}
