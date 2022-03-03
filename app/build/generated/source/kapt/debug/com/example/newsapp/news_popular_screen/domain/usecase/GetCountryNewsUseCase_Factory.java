// Generated by Dagger (https://dagger.dev).
package com.example.newsapp.news_popular_screen.domain.usecase;

import com.example.newsapp.news_popular_screen.domain.repository.PopularNewsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class GetCountryNewsUseCase_Factory implements Factory<GetCountryNewsUseCase> {
  private final Provider<PopularNewsRepository> repositoryProvider;

  public GetCountryNewsUseCase_Factory(Provider<PopularNewsRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetCountryNewsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetCountryNewsUseCase_Factory create(
      Provider<PopularNewsRepository> repositoryProvider) {
    return new GetCountryNewsUseCase_Factory(repositoryProvider);
  }

  public static GetCountryNewsUseCase newInstance(PopularNewsRepository repository) {
    return new GetCountryNewsUseCase(repository);
  }
}
