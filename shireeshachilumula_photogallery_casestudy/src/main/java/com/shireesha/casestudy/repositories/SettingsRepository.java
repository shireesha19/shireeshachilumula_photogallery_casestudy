package com.shireesha.casestudy.repositories;

import com.shireesha.casestudy.models.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
}
